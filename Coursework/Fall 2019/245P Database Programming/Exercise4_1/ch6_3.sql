SELECT v.vendor_name, COUNT(*) AS invoice_qt, SUM(i.invoice_total) AS invoice_total_sum
FROM vendors v JOIN invoices i 
ON v.vendor_id = i.vendor_id
GROUP BY v.vendor_name
ORDER BY COUNT(i.invoice_id) DESC;
