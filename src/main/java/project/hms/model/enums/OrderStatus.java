package project.hms.model.enums;

/**
 * 订单状态：未支付，已支付，已入住，支付完成，超期，取消
 */
public enum OrderStatus {
    UNPAID,
    PAID,
    CHECK_IN,
    COMPLETED,
    NOSHOW,
    CANCEL
}
