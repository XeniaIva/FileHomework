package domain;

public class Shop {

        private String name;
        private String[] products;
        private String address;
        private Integer revenue;
        private Owner owner;

        public String getName() {
            return name;
        }

        public String[] getProducts() {
            return products;
        }

        public String getAddress() {
            return address;
        }

        public Integer getRevenue() {
            return revenue;
        }

        public Owner getOwner() {
            return owner;
        }

        public static class Owner {
            private String ownerName;

            public String getOwnerName() {
                return ownerName;
            }
        }
}
