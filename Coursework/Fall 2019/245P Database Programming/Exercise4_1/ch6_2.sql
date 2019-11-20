SELECT v.vendor_name, SUM(i.payment_total) AS payment_total_sum
FROM vendors v JOIN invoices i
ON v.vendor_id = i.vendor_id
GROUP BY v.vendor_name
ORDER BY sum(i.payment_total) DESC;
