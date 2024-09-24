package com.example.sc;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.AlipayConfig;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.domain.SignData;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.FileItem;
import com.alipay.api.response.AlipayTradePagePayResponse;

import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class AlipayFundTransUniTransfer {

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2021000122609647","MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC6Eyheos0ApIWXsssIDzyiXQJxOFGdiuoSHle4PLl2+Bkureuj1c0wsJZhCJ5l44SH8RIdRHyts40u0uhajFDRO14+VkVR07tEzA5CnAYeGoEW0ByzeqcFqDkMARcw0w/l6WaisdlerFlbscL9b5stFENo871sFBIbLvhalFQ5aLYg0EnH0RzwoPGt/TxOytfCjoN5TCp5Ocu+4qKBkJJRGSkgNWQauI2V2VOww0nSrk8SX4OWii1obuumyGomovSijFB1fNs7MNiMivB+q5tHTs1uAp9TlUwETbxRocwqAv1fhHc1B7rL8/nLW1LzpjKOssOOVo0mU8k03io1q91nAgMBAAECggEAKpXt3FLYJjiBFqnly69pCiPNRkpEa5D6ZzEolFLZg3/ei49kabep4WXY1NZbIiiDEXW71pTxD/QS8M92PBr2+BvlQtENxl918ZXeAaKhx8v7FVSF7RG6HhJVFAk/3H3BmrXjNoMLo55ASTsSzFv5oMSNdPyrsouZZT1Hu7tM12JaQZCwA22sncZQaI/gskgbzVYv3MdywN6R1IMTpC7WF4K5BqBj8lbohwR4kL1599zyNWuaqO6zFK61ZdYB9T+8HPC/DDlQ6Oa4/JwccCfE9khQQmsiN4yyEl4w5aqwVT3G4luvsdLuQs/CwhUy07N7nQ9OXS/gMMRB/JiE2KY/aQKBgQDw0rchxETuGlwtSJcjw0zlW7AeL9Rb7md8SilgZLF5aMC7D3AawFyFMW1/+gDdDsSEoY9c+CRDUCl5bZlEgTDkwhn9tGu2Pw0w0bD7k4k6IWtE9VCvjkHMIQ9JCIZvcuxaNx+Pzk8dpFtG33/0YSziQfLk6VyfqIfPErl0phMYLQKBgQDFzStFvnZi+LIQ9uFAs+URslNf/x2Grm+/ugDjo1cugm/Nvd1+97FogJmk5LAvMifZHNwY0ZzJhd4DkEHW/lKmrSSl25noEPOCQqiCrdt6UncXkpAqZ3Rpqg/z/w6G9Y4B5hwxtK0b1hjrax192/ILw2f/VoLxDC+SajHt4b4UYwKBgB+AoP4ky5OOFTLDRSojeSKjrNU2EoR4Gkdy+Bio82JbZqLRsSt+sFuoDJfqH0TLkr961uMlfrDjgplk0eDZOYkjLv02JcTwWIxjUdCHCzN0fbDGmmhspoTKalJ2cAeSNALVJSbNqSHKhzotMHLKZ3GtiNWTBU/JXwKig2oT83l9AoGAYAXc1uvCPwOqNfATo7xxHKksLnMDm2k9GJ8sZVXbMdnieseMKmIGPswHSnQ2T4MSsyR+eJHxgic5gxNZ0wv9haqSGu5DhdQM0pSXh3DtFjHIXKoq0FsOuWj0e6e7HqtAOA3EbCDg1J2X1VlXpMzMcQd4ig0uXeRjh7mD6Y5oHy8CgYBCqfIxW9yhSQ4eM+pojb/8jBshI0D+Jy7cHDrNSZUdsJo14yFTDwM+Mmi7uS9/dkChT8+ut5Hmujvz0sHUy4Ukurr0zsRHmRigboH2ejSXfRgP3wJgaGBVwWjLchKCaHaxkVFyb6JDfHWEbY5xBuqYWBPpuTe+0B9ugJJpDAXV6g==","json","GBK","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuhMoXqLNAKSFl7LLCA88ol0CcThRnYrqEh5XuDy5dvgZLq3ro9XNMLCWYQieZeOEh/ESHUR8rbONLtLoWoxQ0TtePlZFUdO7RMwOQpwGHhqBFtAcs3qnBag5DAEXMNMP5elmorHZXqxZW7HC/W+bLRRDaPO9bBQSGy74WpRUOWi2INBJx9Ec8KDxrf08TsrXwo6DeUwqeTnLvuKigZCSURkpIDVkGriNldlTsMNJ0q5PEl+DlootaG7rpshqJqL0ooxQdXzbOzDYjIrwfqubR07NbgKfU5VMBE28UaHMKgL9X4R3NQe6y/P5y1tS86YyjrLDjlaNJlPJNN4qNavdZwIDAQAB","RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl("");
//同步跳转地址，仅支持http/https
        request.setReturnUrl("");
/******必传参数******/
        JSONObject bizContent = new JSONObject();
//商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", "20210817010155054");
//支付金额，最小值0.01元
        bizContent.put("total_amount", 25);
//订单标题，不可使用特殊符号
        bizContent.put("subject", "测试商品");
//电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

/******可选参数******/
//bizContent.put("time_expire", "2022-08-01 22:00:00");

//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//bizContent.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

        System.out.println(response.getBody());
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
