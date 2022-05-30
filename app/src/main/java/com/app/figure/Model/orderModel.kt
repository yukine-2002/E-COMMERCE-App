package com.app.figure.Model

data class orderModel(
    val TotalPrice: String,
    val cart: ArrayList<cartModel>,
    val id_cus: Int,
    val id_pros: Int,
    val paypay: String,
    val voucher: Any?
)

data class Cart(
    val id_pro: Int,
    val img: String,
    val name_pro: String,
    val price: String,
    val quantity: Int
)

data class vnpayReturn(
    val vnp_Amount: String,
    val vnp_BankCode: String,
    val vnp_BankTranNo: String,
    val vnp_CardType: String,
    val vnp_OrderInfo: String,
    val vnp_PayDate: String,
    val vnp_ResponseCode: String,
    val vnp_SecureHash: String,
    val vnp_TmnCode: String,
    val vnp_TransactionNo: String,
    val vnp_TransactionStatus: String,
    val vnp_TxnRef: String
)

data class dataConfimVnPay(
    val id_cus: Int,
    val id_pros: Int,
    val cart: ArrayList<cartModel>,
    val vnp_Amount: String,
    val vnp_TxnRef: String
)


data class getDataOrderModel(
    val created_at: String,
    val dates: String,
    val id_ord: Int,
    val id_pro: Any,
    val id_user: Int,
    val id_voucher: Any,
    val listId_pr: String,
    val maGD: String,
    val payBy: String,
    val quantity: String,
    val statuss: Int,
    val tien: String,
    val updated_at: String
)