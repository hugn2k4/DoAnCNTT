package com.laptop.service;

import com.laptop.utils.MailUtils;
import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPService {
    private static final int OTP_LENGTH = 6; // Độ dài của mã OTP
    private final Map<String, String> otpStorage = new HashMap<>(); // Lưu trữ OTP tạm thời

    /**
     * Tạo mã OTP cho một email.
     *
     * @param email Địa chỉ email của người dùng.
     * @return Mã OTP đã tạo.
     */
    public String generateOtp(String email) {
        String otp = generateRandomOtp();
        otpStorage.put(email, otp); // Lưu mã OTP vào bộ nhớ tạm
        return otp;
    }

    /**
     * Lấy mã OTP đã lưu cho email.
     *
     * @param email Địa chỉ email của người dùng.
     * @return Mã OTP hoặc null nếu không tồn tại.
     */
    public String getOtp(String email) {
        return otpStorage.get(email);
    }

    /**
     * Xóa mã OTP sau khi đã sử dụng.
     *
     * @param email Địa chỉ email của người dùng.
     */
    public void removeOtp(String email) {
        otpStorage.remove(email);
    }

    /**
     * Gửi OTP qua email (mô phỏng).
     *
     * @param email Địa chỉ email của người dùng.
     * @param otp   Mã OTP cần gửi.
     */
    public void sendOtpToEmail(String email, String otp) throws MessagingException {
        // Đây chỉ là mô phỏng việc gửi email.
        // Trong thực tế, bạn cần sử dụng một thư viện như JavaMail API để gửi email.
        String context = buildOtp(email, otp);
        MailUtils.sendMail(email,"Xác nhận tài khoản",context);
    }

    /**
     * Kiểm tra mã OTP có khớp với email hay không.
     *
     * @param email Địa chỉ email của người dùng.
     * @param otp   Mã OTP được nhập.
     * @return true nếu khớp, false nếu không khớp.
     */
    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        return otp != null && otp.equals(storedOtp);
    }

    /**
     * Tạo một mã OTP ngẫu nhiên.
     *
     * @return Mã OTP ngẫu nhiên.
     */
    private String generateRandomOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Thêm số ngẫu nhiên (0-9)
        }
        return otp.toString();
    }

    public String buildOtp(String email, String otp) {
        return "<html>" +
                "<body>" +
                "<h1 style='color: blue;'>Xác Nhận Mã OTP</h1>" +
                "<p>Kính gửi Quý khách hàng,</p>" +
                "<p>Cảm ơn khách hàng: <b>" + email + "</b> đã đăng ký tài khoản LaptopShop.</p>" +
                "<p>Vui lòng nhập mã OTP dưới đây để hoàn tất quá trình xác thực:</p>" +
                "<h2 style='color: green;'>Mã OTP của bạn: <b>" + otp + "</b></h2>" +
                "<p>Mã OTP này có hiệu lực trong vòng 5 phút. Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>" +
                "<p>Nếu bạn gặp bất kỳ vấn đề nào, hãy liên hệ với chúng tôi qua email: " +
                "<a href=\"mailto:support@example.com\">support@example.com</a> hoặc hotline: 123-456-7890.</p>" +
                "<p>Trân trọng,</p>" +
                "<p><b>Đội ngũ hỗ trợ LatopShop</b></p>" +
                "</body>" +
                "</html>";

    }
}
