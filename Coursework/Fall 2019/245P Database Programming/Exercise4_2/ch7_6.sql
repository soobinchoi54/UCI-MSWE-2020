SELECT MIN(v.vendor_name) AS vendor_name, v.vendor_city, v.vendor_state
FROM vendors v
GROUP BY v.vendor_city, v.vendor_state
HAVING COUNT(*) = 1;