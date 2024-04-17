package pt.isec.pa.tinypac.model.data.utils;

public enum Direction {
    UP {
        @Override
        public String toString() {
            return "up";
        }
    },
    RIGHT {
        @Override
        public String toString() {
            return "right";
        }
    },
    DOWN {
        @Override
        public String toString() {
            return "down";
        }
    },
    LEFT {
        @Override
        public String toString() {
            return "left";
        }
    },
    NONE {
        @Override
        public String toString() {
            return "none";
        }
    }

}
