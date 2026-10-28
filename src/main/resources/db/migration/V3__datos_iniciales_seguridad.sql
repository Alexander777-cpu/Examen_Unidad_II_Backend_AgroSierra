-- 1. PRIVILEGIOS
INSERT INTO privilegios (id, nombre, descripcion) VALUES (1, 'LOTE_LEER', 'Consultar el padron y detalle de lotes');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (2, 'LABOR_LEER', 'Consultar las labores de un lote');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (3, 'LABOR_CREAR', 'Registrar una labor nueva');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (4, 'LABOR_EDITAR', 'Corregir una labor existente');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (5, 'LABOR_ELIMINAR', 'Eliminar una labor');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (6, 'CAMPANIA_CERRAR', 'Cerrar la campania de un lote');
INSERT INTO privilegios (id, nombre, descripcion) VALUES (7, 'USUARIO_GESTIONAR', 'Crear usuarios y asignar roles');

-- 2. ROLES
INSERT INTO roles (id, nombre, descripcion) VALUES (1, 'TECNICO', 'Tecnico de campo');
INSERT INTO roles (id, nombre, descripcion) VALUES (2, 'JEFE_CAMPO', 'Jefe de campo');
INSERT INTO roles (id, nombre, descripcion) VALUES (3, 'ADMINISTRADOR', 'Administrador general del sistema');

-- 3. ROL_PRIVILEGIOS (Sección 3.3)
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (1, 1);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (1, 2);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (1, 3);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (1, 4);

INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 1);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 2);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 3);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 4);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 5);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (2, 6);

INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (3, 1);
INSERT INTO rol_privilegios (rol_id, privilegio_id) VALUES (3, 7);

-- 4. USUARIOS (BCrypt de 'Agro2026*')
INSERT INTO usuarios (id, username, password, nombre_completo, estado, fecha_creacion)
VALUES (1, 'tecnico1', '$2a$10$7Z8KqDqAUpB0lZkQ1F7Qze6.s/h6bX2mB1Qd8xXqE5O2N5U9iF7Z.', 'Juan Perez Tecnico', 1, CURRENT_TIMESTAMP);

INSERT INTO usuarios (id, username, password, nombre_completo, estado, fecha_creacion)
VALUES (2, 'jefe1', '$2a$10$7Z8KqDqAUpB0lZkQ1F7Qze6.s/h6bX2mB1Qd8xXqE5O2N5U9iF7Z.', 'Carlos Ramos Jefe', 1, CURRENT_TIMESTAMP);

INSERT INTO usuarios (id, username, password, nombre_completo, estado, fecha_creacion)
VALUES (3, 'admin', '$2a$10$7Z8KqDqAUpB0lZkQ1F7Qze6.s/h6bX2mB1Qd8xXqE5O2N5U9iF7Z.', 'Administrador Sistema', 1, CURRENT_TIMESTAMP);

-- 5. USUARIO_ROLES
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (3, 3);

-- 6. PADRÓN DE LOTES (8 lotes en 3 valles)
INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (1, 'LOT-001', 'La Quebrada', 5.50, 'Papas Nativas', 'Valle Mantaro', 0, 4.00);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (2, 'LOT-002', 'El Mirador', 3.20, 'Maiz Morado', 'Valle Mantaro', 0, 3.50);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (3, 'LOT-003', 'Las Palmeras', 8.00, 'Palta Hass', 'Valle Sagrado', 0, 5.00);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (4, 'LOT-004', 'Bella Vista', 4.50, 'Cafe Geisha', 'Valle Chanchamayo', 0, 3.00);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (5, 'LOT-005', 'San Isidro', 6.00, 'Cacao Criollo', 'Valle Chanchamayo', 0, 4.50);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (6, 'LOT-006', 'El Olivar', 2.80, 'Quinua Real', 'Valle Sagrado', 0, 2.50);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (7, 'LOT-007', 'Santa Elena', 7.00, 'Trigo Serrado', 'Valle Mantaro', 1, 3.00);

INSERT INTO lotes (id, codigo, nombre, hectareas, cultivo, valle, campania_cerrada, dosis_maxima_por_hectarea)
VALUES (8, 'LOT-008', 'Los Sauces', 4.00, 'Arveja Verde', 'Valle Sagrado', 1, 2.80);

-- 7. LABORES (20 labores de prueba)
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (1, 'RIEGO', '2026-10-15', 'tecnico1', 120.00, 'Riego por goteo inicial', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (1, 'FUMIGACION', '2026-10-18', 'tecnico1', 18.00, 'Control preventivo de plagas', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (1, 'COSECHA', '2026-10-25', 'tecnico1', 850.00, 'Primera saca de papa nativa', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (2, 'RIEGO', '2026-10-12', 'tecnico1', 95.00, 'Riego canal principal', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (2, 'FUMIGACION', '2026-10-20', 'tecnico1', 8.50, 'Aplicacion contra cogollero', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (3, 'RIEGO', '2026-10-10', 'tecnico1', 200.00, 'Riego por aspersion sector A', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (3, 'FUMIGACION', '2026-10-17', 'tecnico1', 35.00, 'Nutricion foliar de palta', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (3, 'RIEGO', '2026-10-24', 'tecnico1', 190.00, 'Riego programado', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (4, 'RIEGO', '2026-10-08', 'tecnico1', 80.00, 'Humedad base cafetal', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (4, 'FUMIGACION', '2026-10-14', 'tecnico1', 12.00, 'Fungicida contra roya', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (4, 'COSECHA', '2026-10-26', 'tecnico1', 400.00, 'Recoleccion selectiva grano maduro', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (5, 'RIEGO', '2026-10-09', 'tecnico1', 150.00, 'Riego sector bajo', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (5, 'FUMIGACION', '2026-10-19', 'tecnico1', 22.00, 'Control fitosanitario mazorca', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (5, 'RIEGO', '2026-10-25', 'tecnico1', 140.00, 'Riego de soporte', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (6, 'RIEGO', '2026-10-11', 'tecnico1', 60.00, 'Riego ligero floracion', CURRENT_TIMESTAMP);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (6, 'FUMIGACION', '2026-10-21', 'tecnico1', 6.00, 'Aplicacion biologica', CURRENT_TIMESTAMP);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (7, 'RIEGO', '2026-09-01', 'tecnico1', 110.00, 'Riego previo', CURRENT_TIMESTAMP - INTERVAL '30' DAY);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (7, 'COSECHA', '2026-09-20', 'tecnico1', 1200.00, 'Cosecha mecanizada completa', CURRENT_TIMESTAMP - INTERVAL '20' DAY);

INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (8, 'RIEGO', '2026-09-05', 'tecnico1', 75.00, 'Riego unico', CURRENT_TIMESTAMP - INTERVAL '35' DAY);
INSERT INTO labores (lote_id, tipo, fecha, responsable, cantidad, observacion, fecha_registro)
VALUES (8, 'COSECHA', '2026-09-28', 'tecnico1', 520.00, 'Cosecha de campania', CURRENT_TIMESTAMP - INTERVAL '15' DAY);

COMMIT;