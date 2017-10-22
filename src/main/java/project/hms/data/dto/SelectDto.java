package project.hms.data.dto;

public class SelectDto {

    private boolean select = false;
    private boolean multi = false;
    private String action = null;
    private String method = "post";

    public SelectDto() {
        this.select = false;
    }

    public boolean isSelect() {
        return select || multi;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
