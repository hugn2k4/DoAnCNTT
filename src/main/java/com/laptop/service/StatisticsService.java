package com.laptop.service;

import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class StatisticsService {

    public List<Object[]> getDailyStatistics() {
        String hqlByDay = "SELECT DAY(u.registrationDate) AS day, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE MONTH(u.registrationDate) = MONTH(CURRENT_DATE) " +
                "AND YEAR(u.registrationDate) = YEAR(CURRENT_DATE) " +
                "GROUP BY DAY(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByDay, Object[].class);
            return query.list();
        }
    }

    public List<Object[]> getMonthlyStatisticsCurrentYear() {
        String hqlByMonthCurrentYear = "SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE YEAR(u.registrationDate) = YEAR(CURRENT_DATE) " +
                "GROUP BY MONTH(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthCurrentYear, Object[].class);
            return query.list();
        }
    }

    public List<Object[]> getMonthlyStatisticsLastYear() {
        String hqlByMonthLastYear = "SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE YEAR(u.registrationDate) = YEAR(CURRENT_DATE) - 1 " +
                "GROUP BY MONTH(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthLastYear, Object[].class);
            return query.list();
        }
    }
}
