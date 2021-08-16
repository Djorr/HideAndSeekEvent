package nl.rubixstudios.hideandseek.util.item;

public class ItemDurabilityUtil {

    /**
     * Calculates the new armor damage
     * @param armorDamage the default/current armor damage
     * @param percentage the percentage to give
     * @return returns the result by doing 'armorDamage / 100 * percentage'
     */
    public static int calculateNewArmorDamage(final int armorDamage, final int percentage) {
        return armorDamage - (armorDamage / 100 * percentage);
    }
}

