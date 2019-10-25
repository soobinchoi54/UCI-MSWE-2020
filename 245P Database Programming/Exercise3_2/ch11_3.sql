INSERT INTO members
VALUE(DEFAULT, 'John', 'Doe', '5 Chattanooga', 'Irvine', 'CA', '1625364747');
INSERT INTO members
VALUES (DEFAULT, 'Mary', 'Fisher', '11 Chesapeake', 'Irvine', 'CA', '4857463737');

INSERT INTO committees
VALUE(DEFAULT, "Hiking Crew");
INSERT INTO committees
VALUE(DEFAULT, "Climbing Club");

INSERT INTO members_committees
VALUE(1,1);
INSERT INTO members_committees
VALUE(2,1);
INSERT INTO members_committees
VALUE(1,2);

SELECT c.committee_name, m.last_name, m.first_name
FROM committees c
  JOIN members_committees mc
    ON c.committee_id = mc.committee_id
  JOIN members m
    ON mc.member_id = m.member_id
ORDER BY c.committee_name, m.last_name, m.first_name;