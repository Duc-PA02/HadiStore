package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.MailInfo;
import com.example.hadistore.entity.Order;
import com.example.hadistore.entity.OrderDetail;
import com.example.hadistore.repository.OrderDetailRepository;
import com.example.hadistore.service.SendMailService;
import com.example.hadistore.utils.TemplateMail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendMailServiceImpl implements SendMailService {
    JavaMailSender javaMailSender;
    OrderDetailRepository orderDetailRepository;
    Queue<MailInfo> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfo(to, subject, body));
    }

    @Override
    public void queue(MailInfo mail) {
        queue.offer(mail);
    }

    @Override
    public void send(MailInfo mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());

        if (mail.getAttachments() != null) {
            FileSystemResource file = new FileSystemResource(new File(mail.getAttachments()));
            helper.addAttachment(mail.getAttachments(), file);
        }
        javaMailSender.send(message);
    }

    @Override
    public void sendOtp(String email, int otp, String title) {
        String body = "<div>\r\n" + "        <h3>Mã OTP của bạn là: <span style=\"color:red; font-weight: bold;\">"
                + otp + "</span></h3>\r\n" + "    </div>";
        queue(email, title, body);
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public void run() {
        while (!queue.isEmpty()) {
            MailInfo mail = queue.poll();
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMailOrder(Order order) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        List<OrderDetail> listOrderDetails = orderDetailRepository.findByOrder(order);
        StringBuilder content = new StringBuilder();
        content.append(TemplateMail.HEADER);
        for (OrderDetail oderDetail : listOrderDetails) {
            content.append("<tr>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;text-align: center;\">\r\n"
                    + "                                                        <img style=\"width: 85%;\" src="
                    + oderDetail.getProduct().getImage() + ">\r\n"
                    + "                                                    </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getProduct().getName() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getQuantity() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + format(String.valueOf(oderDetail.getPrice())) + " </td>\r\n"
                    + "                                                </tr>");
        }
        content.append(TemplateMail.BODY2);
        content.append(
                "<td width=\"55%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\"> Tổng tiền: </td>\r\n"
                        + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee; color: red;\"> "
                        + format(String.valueOf(order.getAmount())) + " </td>");
        content.append(TemplateMail.BODY3);
        content.append(
                "<td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Địa chỉ giao hàng</p>\r\n"
                        + "                                                            <p>" + order.getAddress()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Ngày đặt hàng</p>\r\n"
                        + "                                                            <p>"
                        + dt.format(order.getOrderDate()) + "</p>\r\n"
                        + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Tên người nhận</p>\r\n"
                        + "                                                            <p>" + order.getUser().getName()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Số điện thoại</p>\r\n"
                        + "                                                            <p>" + order.getPhone()
                        + "</p>\r\n" + "                                                        </td>");
        content.append(TemplateMail.FOOTER);
        queue(order.getUser().getEmail(), "Đặt hàng thành công", content.toString());
    }
    public void sendMailOrderSuccess(Order order) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        List<OrderDetail> listOrderDetails = orderDetailRepository.findByOrder(order);
        StringBuilder content = new StringBuilder();
        content.append(TemplateMail.HEADERSUCCESS);
        for (OrderDetail oderDetail : listOrderDetails) {
            content.append("<tr>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;text-align: center;\">\r\n"
                    + "                                                        <img style=\"width: 85%;\" src="
                    + oderDetail.getProduct().getImage() + ">\r\n"
                    + "                                                    </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getProduct().getName() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getQuantity() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + format(String.valueOf(oderDetail.getPrice())) + " </td>\r\n"
                    + "                                                </tr>");
        }
        content.append(TemplateMail.BODY2);
        content.append(
                "<td width=\"55%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\"> Tổng tiền: </td>\r\n"
                        + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee; color: red;\"> "
                        + format(String.valueOf(order.getAmount())) + " </td>");
        content.append(TemplateMail.BODY3);
        content.append(
                "<td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Địa chỉ giao hàng</p>\r\n"
                        + "                                                            <p>" + order.getAddress()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Ngày đặt hàng</p>\r\n"
                        + "                                                            <p>"
                        + dt.format(order.getOrderDate()) + "</p>\r\n"
                        + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Tên người nhận</p>\r\n"
                        + "                                                            <p>" + order.getUser().getName()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Số điện thoại</p>\r\n"
                        + "                                                            <p>" + order.getPhone()
                        + "</p>\r\n" + "                                                        </td>");
        content.append(TemplateMail.FOOTER);
        queue(order.getUser().getEmail(), "Thanh toán thành công", content.toString());
    }

    public void sendMailOrderDeliver(Order order) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        List<OrderDetail> listOrderDetails = orderDetailRepository.findByOrder(order);
        StringBuilder content = new StringBuilder();
        content.append(TemplateMail.HEADERDELIVER);
        for (OrderDetail oderDetail : listOrderDetails) {
            content.append("<tr>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;text-align: center;\">\r\n"
                    + "                                                        <img style=\"width: 85%;\" src="
                    + oderDetail.getProduct().getImage() + ">\r\n"
                    + "                                                    </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getProduct().getName() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getQuantity() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + format(String.valueOf(oderDetail.getPrice())) + " </td>\r\n"
                    + "                                                </tr>");
        }
        content.append(TemplateMail.BODY2);
        content.append(
                "<td width=\"55%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\"> Tổng tiền: </td>\r\n"
                        + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee; color: red;\"> "
                        + format(String.valueOf(order.getAmount())) + " </td>");
        content.append(TemplateMail.BODY3);
        content.append(
                "<td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Địa chỉ giao hàng</p>\r\n"
                        + "                                                            <p>" + order.getAddress()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Ngày đặt hàng</p>\r\n"
                        + "                                                            <p>"
                        + dt.format(order.getOrderDate()) + "</p>\r\n"
                        + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Tên người nhận</p>\r\n"
                        + "                                                            <p>" + order.getUser().getName()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Số điện thoại</p>\r\n"
                        + "                                                            <p>" + order.getPhone()
                        + "</p>\r\n" + "                                                        </td>");
        content.append(TemplateMail.FOOTER);
        queue(order.getUser().getEmail(), "Đơn hàng đã được xác nhận", content.toString());
    }

    public void sendMailOrderCancel(Order order) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        List<OrderDetail> listOrderDetails = orderDetailRepository.findByOrder(order);
        StringBuilder content = new StringBuilder();
        content.append(TemplateMail.HEADERCANCEL);
        for (OrderDetail oderDetail : listOrderDetails) {
            content.append("<tr>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;text-align: center;\">\r\n"
                    + "                                                        <img style=\"width: 85%;\" src="
                    + oderDetail.getProduct().getImage() + ">\r\n"
                    + "                                                    </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getProduct().getName() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + oderDetail.getQuantity() + " </td>\r\n"
                    + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 18px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\"> "
                    + format(String.valueOf(oderDetail.getPrice())) + " </td>\r\n"
                    + "                                                </tr>");
        }
        content.append(TemplateMail.BODY2);
        content.append(
                "<td width=\"55%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\"> Tổng tiền: </td>\r\n"
                        + "                                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee; color: red;\"> "
                        + format(String.valueOf(order.getAmount())) + " </td>");
        content.append(TemplateMail.BODY3);
        content.append(
                "<td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Địa chỉ giao hàng</p>\r\n"
                        + "                                                            <p>" + order.getAddress()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Ngày đặt hàng</p>\r\n"
                        + "                                                            <p>"
                        + dt.format(order.getOrderDate()) + "</p>\r\n"
                        + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Tên người nhận</p>\r\n"
                        + "                                                            <p>" + order.getUser().getName()
                        + "</p>\r\n" + "                                                        </td>\r\n"
                        + "                                                    </tr>\r\n"
                        + "                                                </table>\r\n"
                        + "                                            </div>\r\n"
                        + "                                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\r\n"
                        + "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\r\n"
                        + "                                                    <tr>\r\n"
                        + "                                                        <td align=\"center\" valign=\"top\" style=\"font-family: Open sans-serif; font-size: 20px; font-weight: 400; line-height: 24px;\">\r\n"
                        + "                                                            <p style=\"font-weight: 800;\">Số điện thoại</p>\r\n"
                        + "                                                            <p>" + order.getPhone()
                        + "</p>\r\n" + "                                                        </td>");
        content.append(TemplateMail.FOOTER);
        queue(order.getUser().getEmail(), "Huỷ đơn thành công", content.toString());
    }
    private String format(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");

        return formatter.format(Double.valueOf(number)) + " VNĐ";
    }
}
