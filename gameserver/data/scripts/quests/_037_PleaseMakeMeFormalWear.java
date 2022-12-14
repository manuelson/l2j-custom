package quests;

import l2f.gameserver.model.instances.NpcInstance;
import l2f.gameserver.model.quest.Quest;
import l2f.gameserver.model.quest.QuestState;
import l2f.gameserver.scripts.ScriptFile;
import l2f.gameserver.templates.item.ItemTemplate;

public class _037_PleaseMakeMeFormalWear extends Quest implements ScriptFile
{
	// NPC's
	private static final int trader_alexis = 30842;
	private static final int leikar = 31520;
	private static final int jeremy = 31521;
	private static final int mist = 31627;

	// QUEST ITEM's
	private static final int q_mysterious_cloth = 7076;
	private static final int q_box_of_jewel = 7077;
	private static final int q_workbox = 7078;
	private static final int q_box_of_dress_shoes = 7113;
	private static final int q_seal_of_stock = 7164;
	private static final int q_luxury_wine = 7160;
	private static final int q_box_of_cookies = 7159;

	@Override
	public void onLoad()
	{}

	@Override
	public void onReload()
	{}

	@Override
	public void onShutdown()
	{}

	public _037_PleaseMakeMeFormalWear()
	{
		super(false);
		addStartNpc(trader_alexis);
		addTalkId(trader_alexis, leikar, jeremy, mist);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equals("quest_accept"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "trader_alexis_q0037_0104.htm";
		}
		else if (event.equals("reply_1"))
		{
			st.setCond(2);
			st.giveItems(q_seal_of_stock, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "leikar_q0037_0201.htm";
		}
		else if (event.equals("reply_1_1"))
		{
			st.setCond(6);
			st.takeItems(q_box_of_cookies, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "leikar_q0037_0601.htm";
		}
		else if (event.equals("reply_1_1_1"))
		{
			if (st.getQuestItemsCount(q_mysterious_cloth) >= 1 && st.getQuestItemsCount(q_box_of_jewel) >= 1 && st.getQuestItemsCount(q_workbox) >= 1)
			{
				st.setCond(7);
				st.takeItems(q_mysterious_cloth, 1);
				st.takeItems(q_box_of_jewel, 1);
				st.takeItems(q_workbox, 1);
				st.playSound(SOUND_MIDDLE);
				htmltext = "leikar_q0037_0701.htm";
			}
			else
				htmltext = "leikar_q0037_0702.htm";
		}
		else if (event.equals("reply_3"))
		{
			if (st.getQuestItemsCount(q_box_of_dress_shoes) >= 1 && st.getQuestItemsCount(q_seal_of_stock) >= 1)
			{
				st.takeItems(q_box_of_dress_shoes, 1);
				st.takeItems(q_seal_of_stock, 1);
				st.giveItems(ItemTemplate.ITEM_ID_FORMAL_WEAR, 1);
				st.unset("cond");
				htmltext = "leikar_q0037_0801.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
				htmltext = "leikar_q0037_0802.htm";
		}
		else if (event.equals("reply_1a"))
		{
			st.setCond(3);
			st.giveItems(q_luxury_wine, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "jeremy_q0037_0301.htm";
		}
		else if (event.equals("reply_1b"))
		{
			st.setCond(5);
			st.giveItems(q_box_of_cookies, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "jeremy_q0037_0301.htm";
		}
		else if (event.equals("reply_1c"))
		{
			if (st.getQuestItemsCount(q_luxury_wine) >= 1)
			{
				st.setCond(4);
				st.takeItems(q_luxury_wine, 1);
				st.playSound(SOUND_MIDDLE);
				htmltext = "mist_q0037_0401.htm";
			}
			else
				htmltext = "mist_q0037_0402.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getCond();

		if (npcId == trader_alexis)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 60)
					htmltext = "trader_alexis_q0037_0101.htm";
				else
				{
					htmltext = "trader_alexis_q0037_0103.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1)
				htmltext = "trader_alexis_q0037_0105.htm";
		}
		else if (npcId == leikar)
		{
			if (cond == 1)
				htmltext = "leikar_q0037_0101.htm";
			else if (cond == 2)
				htmltext = "leikar_q0037_0202.htm";
			else if (cond == 5 && st.getQuestItemsCount(q_box_of_cookies) >= 1)
				htmltext = "leikar_q0037_0501.htm";
			else if (cond == 6)
			{
				if (st.getQuestItemsCount(q_mysterious_cloth) >= 1 && st.getQuestItemsCount(q_box_of_jewel) >= 1 && st.getQuestItemsCount(q_workbox) >= 1)
					htmltext = "leikar_q0037_0603.htm";
				else
					htmltext = "leikar_q0037_0604.htm";
			}
			else if (cond == 7)
				if (st.getQuestItemsCount(q_box_of_dress_shoes) >= 1)
					htmltext = "leikar_q0037_0703.htm";
				else
					htmltext = "leikar_q0037_0704.htm";
		}
		else if (npcId == jeremy)
		{
			if (cond == 2 && st.getQuestItemsCount(q_seal_of_stock) >= 1)
				htmltext = "jeremy_q0037_0201.htm";
			else if (cond == 3)
				htmltext = "jeremy_q0037_0303.htm";
			else if (cond == 4)
				htmltext = "jeremy_q0037_0401.htm";
			else if (cond == 5)
				htmltext = "jeremy_q0037_0502.htm";
		}
		else if (npcId == mist)
			if (cond == 3 && st.getQuestItemsCount(q_luxury_wine) >= 1)
				htmltext = "mist_q0037_0301.htm";
		return htmltext;
	}
}