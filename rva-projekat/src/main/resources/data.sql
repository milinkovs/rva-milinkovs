-- BOLNICA
INSERT INTO bolnica (id, naziv, adresa, budzet) VALUES
(nextval('bolnica_seq'), 'KBC Beograd', 'Pasterova 2, Beograd', 10000000),
(nextval('bolnica_seq'), 'Opšta bolnica Novi Sad', 'Bulevar Evrope 1, Novi Sad', 7500000),
(nextval('bolnica_seq'), 'Klinicki centar Nis', 'Zorana Djindjica 48, Nis', 8000000),
(nextval('bolnica_seq'), 'Zdravstveni centar Subotica', 'Matije Korvina 1, Subotica', 6000000);

-- ODELJENJE
INSERT INTO odeljenje (id, naziv, lokacija, id_bolnice) VALUES
(nextval('odeljenje_seq'), 'Interno odeljenje', 'Sprat 1', 1),
(nextval('odeljenje_seq'), 'Hirurgija', 'Sprat 2', 1),
(nextval('odeljenje_seq'), 'Pedijatrija', 'Sprat 1', 2),
(nextval('odeljenje_seq'), 'Psihijatrija', 'Sprat 3', 3);

-- DIJAGNOZA
INSERT INTO dijagnoza (id, naziv, opis, oznaka) VALUES
(nextval('dijagnoza_seq'), 'Upala pluća', 'Akutna infekcija plućnog tkiva', 'J18'),
(nextval('dijagnoza_seq'), 'Lom ruke', 'Prelom radiusa leve ruke', 'S52.5'),
(nextval('dijagnoza_seq'), 'Depresija', 'Poremećaj raspoloženja', 'F32'),
(nextval('dijagnoza_seq'), 'Groznica', 'Povišena telesna temperatura', 'R50');

-- PACIJENT
INSERT INTO pacijent (id, ime, zdr_osiguranje, datum_rodjenja, id_odeljenja, id_dijagnoze) VALUES
(nextval('pacijent_seq'), 'Petar Petrovic', TRUE, '1990-05-14', 1, 1),
(nextval('pacijent_seq'), 'Marko Markovic', FALSE, '1985-11-23', 2, 2),
(nextval('pacijent_seq'), 'Ana Milovic', TRUE, '2012-07-09', 3, 4),
(nextval('pacijent_seq'), 'Milica Tadic', TRUE, '1995-03-02', 4, 3);
