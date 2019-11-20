SELECT gl.account_description, COUNT(*) AS invoice_li_qt, SUM(li.line_item_amount) AS li_amount_sum
FROM general_ledger_accounts gl JOIN invoice_line_items li
ON gl.account_number = li.account_number
GROUP BY gl.account_description
HAVING invoice_li_qt > 1
ORDER BY li_amount_sum DESC;
