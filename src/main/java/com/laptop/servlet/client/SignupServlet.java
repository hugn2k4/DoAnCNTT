package com.laptop.servlet.client;

import com.laptop.models.User;
import com.laptop.service.OTPService;
import com.laptop.service.UserService;
import com.laptop.utils.HashingUtils;
import com.laptop.utils.Protector;
import com.laptop.utils.Validator;
import jakarta.mail.MessagingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "SignupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final OTPService otpService = new OTPService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signupView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lưu các parameter (tên-giá trị) vào map values
        String action = request.getParameter("action");

        System.out.println("Step: " + action);
        if(action == null) {
            action = "register";
        }
        if ("register".equals(action)) {
            try {
                handleRegistration(request, response);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else if ("verifyOtp".equals(action)) {
            handleOtpVerification(request, response);
        }

    }

    private void handleOtpVerification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputOtp = request.getParameter("otp");
        String generatedOtp = (String) request.getSession().getAttribute("generatedOtp");
        Map<String, String> pendingUser = (Map<String, String>) request.getSession().getAttribute("pendingUser");

        if (inputOtp != null && inputOtp.equals(generatedOtp)) {
            User user = createUserFromSession(pendingUser);
            try {
                userService.insert(user);
                request.setAttribute("successMessage", "Đăng ký thành công!");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Lỗi trong quá trình tạo tài khoản. Vui lòng thử lại.");
                e.printStackTrace();
            }
        } else {
            request.setAttribute("otpError", "Mã OTP không chính xác!");
            request.setAttribute("showOtpForm", true);
        }

        request.getRequestDispatcher("/WEB-INF/views/signupView.jsp").forward(request, response);


    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws MessagingException, ServletException, IOException {
        Map<String, String> values = collectParameters(request);
        Map<String, List<String>> violations = validateInput(values);

        if (violations.values().stream().mapToInt(List::size).sum() == 0) {
            try {
                // Tạo OTP và lưu thông tin người dùng vào session
                String otp = otpService.generateOtp(values.get("email"));
                otpService.sendOtpToEmail(values.get("email"), otp);

                request.getSession().setAttribute("pendingUser", values);
                request.getSession().setAttribute("generatedOtp", otp);

                // Hiển thị form nhập OTP
                request.setAttribute("showOtpForm", true);
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Không thể gửi mã OTP. Vui lòng kiểm tra lại email.");
                e.printStackTrace();
            }
        } else {
            // Gửi lại form đăng ký với thông tin và lỗi
            request.setAttribute("values", values);
            request.setAttribute("violations", violations);
        }

        request.getRequestDispatcher("/WEB-INF/views/signupView.jsp").forward(request, response);

    }


    private Map<String, String> collectParameters(HttpServletRequest request) {
        Map<String, String> values = new HashMap<>();
        values.put("username", request.getParameter("username"));
        values.put("password", request.getParameter("password"));
        values.put("fullname", request.getParameter("fullname"));
        values.put("email", request.getParameter("email"));
        values.put("phoneNumber", request.getParameter("phoneNumber"));
        values.put("gender", request.getParameter("gender"));
        values.put("address", request.getParameter("address"));
        values.put("policy", request.getParameter("policy"));
        return values;
    }


    private Map<String, List<String>> validateInput(Map<String, String> values) {
        Map<String, List<String>> violations = new HashMap<>();
        Optional<User> existingUser = Protector.of(() -> userService.getByUsername(values.get("username"))).get(Optional::empty);

        violations.put("usernameViolations", Validator.of(values.get("username"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .isAtMostOfLength(25)
                .isNotExistent(existingUser.isPresent(), "Tên đăng nhập")
                .toList());
        violations.put("passwordViolations", Validator.of(values.get("password"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .isAtMostOfLength(32)
                .toList());
        violations.put("fullnameViolations", Validator.of(values.get("fullname"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .toList());
        violations.put("emailViolations", Validator.of(values.get("email"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .hasPattern("^[^@]+@[^@]+\\.[^@]+$", "email")
                .toList());
        violations.put("phoneNumberViolations", Validator.of(values.get("phoneNumber"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .hasPattern("^\\d{10,11}$", "số điện thoại")
                .toList());
        violations.put("genderViolations", Validator.of(values.get("gender"))
                .isNotNull()
                .toList());
        violations.put("addressViolations", Validator.of(values.get("address"))
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .toList());
        violations.put("policyViolations", Validator.of(values.get("policy"))
                .isNotNull()
                .toList());

        return violations;
    }


    private User createUserFromSession(Map<String, String> values) {
        return new User(
                0L,
                values.get("username"),
                HashingUtils.hash(values.get("password")),
                values.get("fullname"),
                values.get("email"),
                values.get("phoneNumber"),
                Integer.parseInt(values.get("gender")),
                values.get("address"),
                "CUSTOMER"
        );
    }
}
