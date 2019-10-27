SELECT invoice_due_date AS "Due Date", invoice_total AS "Invoice Total", invoice_total*0.1 AS "10%", invoice_total*1.1 AS "110%"
FROM invoices
WHERE invoice_total >= 500 AND invoice_total <= 1000
ORDER BY invoice_due_date DESC;