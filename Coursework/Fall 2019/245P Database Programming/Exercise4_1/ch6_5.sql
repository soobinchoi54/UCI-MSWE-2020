SELECT gl.account_description, COUNT(*) AS invoice_li_qt, SUM(li.line_item_amount) AS li_amount_sum
FROM invoice_line_items li 
JOIN general_ledger_accounts gl
ON gl.account_number = li.account_number
JOIN invoices i 
ON li.invoice_id = i.invoice_id
WHERE invoice_date BETWEEN '2018-04-01' AND '2018-06-30'
GROUP BY gl.account_description
HAVING invoice_li_qt > 1
ORDER BY li_amount_sum DESC;
