package me.github.arturoatomplay.havenbagspreview;

import java.util.List;

public class BackpackData {
    public List<ItemData> items;

    public static class ItemData {
        private int s;
        private String i;
        private int c;
        private int d;
        private boolean e;
        private int m;
        private String im;

        public int getSlot() { return s; }
        public String getItemName() { return i; }
        public int getCount() { return c; }
        public int getDamage() { return d; }
        public boolean isEnchanted() { return e; }
        public int getModelData() { return m; }
        public String getItemModel() { return im; }
    }
}
