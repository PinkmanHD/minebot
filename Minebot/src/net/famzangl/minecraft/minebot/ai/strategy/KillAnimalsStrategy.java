package net.famzangl.minecraft.minebot.ai.strategy;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.AIStrategy;
import net.famzangl.minecraft.minebot.ai.animals.AnimalyType;
import net.famzangl.minecraft.minebot.ai.selectors.AndSelector;
import net.famzangl.minecraft.minebot.ai.selectors.ItemSelector;
import net.famzangl.minecraft.minebot.ai.selectors.NotSelector;
import net.famzangl.minecraft.minebot.ai.selectors.OrSelector;
import net.famzangl.minecraft.minebot.ai.selectors.OwnTameableSelector;
import net.famzangl.minecraft.minebot.ai.task.AITask;
import net.famzangl.minecraft.minebot.ai.task.FaceAndInteractTask;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;

/**
 * Kills all animals in range by mving towards them and hitting them.
 * 
 * @author michael
 * 
 */
public class KillAnimalsStrategy implements AIStrategy {

	private final class KillableSelector implements IEntitySelector {
		@Override
		public boolean isEntityApplicable(Entity e) {
			if (!type.hasAnimalClass(e)) {
				return false;
			}

			return ((EntityAnimal) e).getGrowingAge() >= 0
					&& ((EntityAnimal) e).getHealth() > 0;
		}
	}

	private static final int DISTANCE = 20;
	private final int maxKills;
	private final AnimalyType type;

	public KillAnimalsStrategy() {
		this(-1, AnimalyType.ANY);
	}

	public KillAnimalsStrategy(int maxKills, AnimalyType type) {
		this.maxKills = maxKills;
		this.type = type;
	}

	@Override
	public void searchTasks(final AIHelper helper) {

		final IEntitySelector selector = new AndSelector(
				new KillableSelector(),
				new NotSelector(new OwnTameableSelector(
						helper.getMinecraft().thePlayer)));

		final IEntitySelector collect = new ItemSelector();

		final Entity found = helper.getClosestEntity(DISTANCE, new OrSelector(
				collect, selector));

		if (found != null) {
			helper.addTask(new FaceAndInteractTask(found, selector, false));
		}
	}

	@Override
	public String getDescription() {
		return "Killing";
	}

	@Override
	public AITask getOverrideTask(AIHelper helper) {
		return null;
	}

}
