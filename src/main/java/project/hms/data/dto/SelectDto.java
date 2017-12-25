package project.hms.data.dto;

/**
 * 后台列表选择信息
 */
public class SelectDto {

    /**
     * 选择状态，为true将会显示列表为选择表单
     */
    private boolean select = false;

    /**
     * 多选，默认为false
     */
    private boolean multi = false;

    /**
     * 表单提交地址
     */
    private String action = null;

    /**
     * 表单提交方式
     */
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
