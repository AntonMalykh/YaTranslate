package com.anykh_dev.yatranslate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


class Translations {
    @SerializedName("head")
    @Expose
    private Object head;
    @SerializedName("def")
    @Expose
    private List<Def> def = new ArrayList<>();

    public List<Def> getDef(){
        return def;
    }

    public void setDef(List<Def> def){
        this.def = def;
    }

    class Def {
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("pos")
        @Expose
        private String pos;
        @SerializedName("tr")
        @Expose
        private List<Tr> tr = new ArrayList<>();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public List<Tr> getTr() {
            return tr;
        }

        public void setTr(List<Tr> tr) {
            this.tr = tr;
        }

        class Tr {

            @SerializedName("text")
            @Expose
            private String text;
            @SerializedName("pos")
            @Expose
            private String pos;
            @SerializedName("syn")
            @Expose
            private List<Syn> syn = null;
            @SerializedName("mean")
            @Expose
            private List<Mean> mean = null;
            @SerializedName("ex")
            @Expose
            private List<Ex> ex = null;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getPos() {
                return pos;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public List<Syn> getSyn() {
                return syn;
            }

            public void setSyn(List<Syn> syn) {
                this.syn = syn;
            }

            public List<Mean> getMean() {
                return mean;
            }

            public void setMean(List<Mean> mean) {
                this.mean = mean;
            }

            public List<Ex> getEx() {
                return ex;
            }

            public void setEx(List<Ex> ex) {
                this.ex = ex;
            }

            class Syn {

                @SerializedName("text")
                @Expose
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

            }

            class Mean {

                @SerializedName("text")
                @Expose
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

            }

            class Ex {

                @SerializedName("text")
                @Expose
                private String text;
                @SerializedName("tr")
                @Expose
                private List<Tr_> tr = null;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<Tr_> getTr() {
                    return tr;
                }

                public void setTr(List<Tr_> tr) {
                    this.tr = tr;
                }

            }

            class Tr_ {

                @SerializedName("text")
                @Expose
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

            }

        }
    }
}
