package fi.helsinki.cs.plugin.tmc.io.zipper.zippingdecider;

/**
 * Zips all the things
 * 
 */
public class ZIP_ALL_THE_THINGS implements ZippingDecider {

    @Override
    public boolean shouldZip(String zipPath) {
        return true;
    }
}