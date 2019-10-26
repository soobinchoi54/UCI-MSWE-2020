SELECT DISTINCT vendor_name
FROM vendors
WHERE vendor_id in
	(SELECT vendor_id
    FROM invoices)
ORDER BY vendor_name;