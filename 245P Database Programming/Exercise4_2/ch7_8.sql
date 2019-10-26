SELECT vendor_name, invoice_number,
       invoice_date, invoice_total
FROM invoices i
    JOIN
    (
      SELECT vendor_id, MIN(invoice_date) AS earliest_invoice
      FROM invoices
      GROUP BY vendor_id
    ) earliest
    ON i.vendor_id = earliest.vendor_id AND
       i.invoice_date = earliest.earliest_invoice
    JOIN vendors v
    ON i.vendor_id = v.vendor_id
ORDER BY vendor_name 
