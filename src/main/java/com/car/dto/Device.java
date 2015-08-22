package com.car.dto;

import java.util.List;

/**
 * Created by nvcuong on 6/17/2015.
 */
public class Device {
    private List<Model> modelList;
    private List<Platform> platformList;
    private List<OS> osList;

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public List<Platform> getPlatformList() {
        return platformList;
    }

    public void setPlatformList(List<Platform> platformList) {
        this.platformList = platformList;
    }

    public List<OS> getOsList() {
        return osList;
    }

    public void setOsList(List<OS> osList) {
        this.osList = osList;
    }

    public static class Model {
        private String  name;
        private int number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return String.format(name + "--" + number);
        }
    }
    public static class Platform {
        private String  os;
        private String  osVersion;
        private int     number;

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return String.format(os + "--" + number);
        }
    }
    public static class OS {
        private String os;
        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }
    }
    public static class Screen {
        private String name;
        private int total;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
