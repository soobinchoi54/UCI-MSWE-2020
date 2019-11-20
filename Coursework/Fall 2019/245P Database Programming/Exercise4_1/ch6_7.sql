SELECT vendor_name, COUNT(DISTINCT(li.account_number)) AS distinct_gl_account
FROM invoices i JOIN vendors v
ON v.vendor_id = i.vendor_id
JOIN invoice_line_items li
ON i.invoice_id = li.invoice_id
GROUP BY v.vendor_name
HAVING distinct_gl_account > 1
ORDER BY v.vendor_name;


