SELECT account_number, SUM(line_item_amount) AS li_amount_sum
FROM invoice_line_items
GROUP BY account_number WITH ROLLUP;
