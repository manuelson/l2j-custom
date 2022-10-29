package zones;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import l2f.commons.threading.RunnableImpl;
import l2f.gameserver.ThreadPoolManager;
import l2f.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2f.gameserver.model.Creature;
import l2f.gameserver.model.Player;
import l2f.gameserver.model.Zone;
import l2f.gameserver.scripts.ScriptFile;
import l2f.gameserver.utils.ReflectionUtils;

public class PvPZone implements ScriptFile
{
	private static ZoneListener _zoneListener;
	private static final Map<Player, Integer> _playerPvps = new HashMap<>();
	
	@Override
	public void onLoad()
	{
		_zoneListener = new ZoneListener();
		Zone zone = ReflectionUtils.getZone("[pvp_zone]");
		zone.addListener(_zoneListener);
	}

	@Override
	public void onReload()
	{
		// on reload
	}

	@Override
	public void onShutdown()
	{
		// on shutdown
	}

	public class ZoneListener implements OnZoneEnterLeaveListener
	{
		@Override
		public void onZoneEnter(Zone zone, Creature cha)
		{
			ScheduledFuture<?> _checkTask = null;
			if (zone.getParams() == null || !cha.isPlayer()) 
				return;
			
			cha.startPvPFlag(null);
			cha.getPlayer().sendMessage("You have entered in a PvP zone!");
			_checkTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new checkZone((Player) cha, zone, _checkTask), 60000, 60000);
		}
		
		public class checkZone extends RunnableImpl
		{
			Player _cha;
			Zone _zone;
			ScheduledFuture<?> _checkTask;
			
			public checkZone(Player cha, Zone zone, ScheduledFuture<?> checkTask)
			{
				_cha = cha;
				_zone = zone;
				_checkTask = checkTask;
			}

			@Override
			public void runImpl()
			{
				if (!_cha.isInZone(_zone))
				{
					_checkTask.cancel(true);
					_checkTask = null;
					return;
				}
				
				// Flag the player
				_cha.startPvPFlag(null);
			}
		}
		
		@Override
		public void onZoneLeave(Zone zone, Creature cha)
		{
			if (cha == null) 
				return;
			
			cha.getPlayer().stopPvPFlag();
			cha.getPlayer().sendMessage("You have left the PvP zone!");
		}

		@Override
		public void onEquipChanged(Zone zone, Creature actor)
		{
		}
	}
	
	public void increasePvp(Player player)
	{
		Integer pvps = _playerPvps.get(player);
		_playerPvps.put(player, pvps != null ? pvps + 1 : 1);
	}
	
	public Player getTopPlayerAndReset()
	{
		int topPvps = 0;
		Player topPvper = null;
		
		for (Map.Entry<Player, Integer> entry : _playerPvps.entrySet())
		{
			int pvp = entry.getValue();
			if (pvp > topPvps)
			{
				topPvps = pvp;
				topPvper = entry.getKey();
			}
		}
		
		_playerPvps.clear();
		return topPvper;
	}
}
