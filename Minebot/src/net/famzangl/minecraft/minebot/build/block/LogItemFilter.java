package net.famzangl.minecraft.minebot.build.block;

import net.famzangl.minecraft.minebot.ai.BlockItemFilter;
import net.minecraft.item.ItemStack;

/**
 * An item filter that filters for a given {@link WoodType}
 * 
 * @author michael
 *
 */
public class LogItemFilter extends BlockItemFilter {

	private final WoodType logType;

	public LogItemFilter(WoodType logType) {
		super(logType.block);
		this.logType = logType;
	}

	@Override
	public boolean matches(ItemStack itemStack) {
		return super.matches(itemStack)
				&& (itemStack.getItemDamage() & 3) == logType.lowerBits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (logType == null ? 0 : logType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LogItemFilter other = (LogItemFilter) obj;
		if (logType != other.logType) {
			return false;
		}
		return true;
	}

	@Override
	public String getDescription() {
		return logType.toString().toLowerCase() + " logs";
	}

}