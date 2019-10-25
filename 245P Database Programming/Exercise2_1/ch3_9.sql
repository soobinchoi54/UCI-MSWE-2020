SELECT vendor_contact_first_name, vendor_contact_last_name, CONCAT(vendor_contact_last_name, ', ', vendor_contact_first_name)
	FROM vendors
    AS full_name
    WHERE vendor_contact_last_name < 'D%' or vendor_contact_last_name LIKE 'E%'
    ORDER BY vendor_contact_last_name, vendor_contact_first_name;
    


