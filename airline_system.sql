-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-05-2026 a las 06:33:11
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `airline_system`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aircrafts`
--

CREATE TABLE `aircrafts` (
  `id` bigint(20) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `aircrafts`
--

INSERT INTO `aircrafts` (`id`, `capacity`, `model`) VALUES
(1, 180, 'Airbus A320'),
(2, 220, 'Boeing 737-800'),
(3, 150, 'Embraer E195'),
(4, 300, 'Boeing 787 Dreamliner'),
(5, 250, 'Airbus A321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `airlines`
--

CREATE TABLE `airlines` (
  `id` bigint(20) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `airlines`
--

INSERT INTO `airlines` (`id`, `country`, `name`) VALUES
(1, 'El Salvador', 'AeroCentro'),
(2, 'México', 'MexFly'),
(3, 'Estados Unidos', 'SkyAmerica'),
(4, 'Colombia', 'Andes Air'),
(5, 'Panamá', 'Canal Wings');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `claims`
--

CREATE TABLE `claims` (
  `id` bigint(20) NOT NULL,
  `claim_date` datetime(6) DEFAULT NULL,
  `claim_type` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `flight_number` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `passenger_name` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `reservation_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `claims`
--

INSERT INTO `claims` (`id`, `claim_date`, `claim_type`, `description`, `destination`, `email`, `flight_number`, `origin`, `passenger_name`, `status`, `reservation_id`) VALUES
(1, '2026-05-18 10:30:00.000000', 'DELAY', 'El vuelo presentó retraso de 3 horas.', 'Ciudad de México', 'carlos@email.com', 'FL001', 'San Salvador', 'Carlos Méndez', 'PENDING', 1),
(2, '2026-05-18 11:00:00.000000', 'LOST_BAGGAGE', 'Equipaje extraviado al llegar.', 'Miami', 'ana@email.com', 'FL002', 'San Salvador', 'Ana López', 'IN_PROGRESS', 2),
(3, '2026-05-18 12:15:00.000000', 'CANCELLATION', 'Vuelo cancelado sin previo aviso.', 'Bogotá', 'luis@email.com', 'FL003', 'San Salvador', 'Luis Ramírez', 'RESOLVED', 3),
(4, '2026-05-18 14:20:00.000000', 'SERVICE', 'Mala atención durante el abordaje.', 'Panamá City', 'maria@email.com', 'FL004', 'San Salvador', 'María Hernández', 'PENDING', 4),
(5, '2026-05-18 15:45:00.000000', 'REFUND', 'Solicitud de reembolso por cancelación.', 'Los Ángeles', 'jose@email.com', 'FL005', 'San Salvador', 'José Martínez', 'APPROVED', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flights`
--

CREATE TABLE `flights` (
  `id` bigint(20) NOT NULL,
  `available_seats` int(11) DEFAULT NULL,
  `departure_time` datetime(6) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `aircraft_id` bigint(20) DEFAULT NULL,
  `airline_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flights`
--

INSERT INTO `flights` (`id`, `available_seats`, `departure_time`, `destination`, `origin`, `price`, `aircraft_id`, `airline_id`) VALUES
(1, 120, '2026-06-10 08:30:00.000000', 'Ciudad de México', 'San Salvador', 250, 1, 2),
(2, 94, '2026-06-11 14:00:00.000000', 'Miami', 'San Salvador', 420, 2, 3),
(3, 76, '2026-06-12 10:15:00.000000', 'Bogotá', 'San Salvador', 310, 3, 4),
(4, 179, '2026-06-13 06:45:00.000000', 'Panamá City', 'San Salvador', 190, 5, 5),
(5, 200, '2026-06-14 16:30:00.000000', 'Los Ángeles', 'San Salvador', 650, 4, 3),
(6, 140, '2026-06-15 12:00:00.000000', 'Guatemala City', 'San Salvador', 150, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `passengers`
--

CREATE TABLE `passengers` (
  `id` bigint(20) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `passport` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `passengers`
--

INSERT INTO `passengers` (`id`, `birth_date`, `fullname`, `passport`) VALUES
(1, '2004-05-14', 'Carlos Méndez', 'SV123456'),
(2, '1999-11-22', 'Ana López', 'SV654321'),
(3, '2001-08-10', 'Luis Ramírez', 'SV112233'),
(4, '1998-03-17', 'María Hernández', 'SV445566'),
(5, '2003-09-30', 'José Martínez', 'SV778899'),
(6, '2002-01-05', 'Nahomy Benítez Reyes', 'SV999888'),
(7, '2007-02-16', 'Nahomy Benítez Reyes', '123456'),
(8, '2007-02-16', 'Nahomy Benítez Reyes', '112233'),
(9, '2007-02-16', 'Nahomy Benítez Reyes', '997755'),
(10, '2007-02-16', 'Nahomy Benítez Reyes', '997766'),
(11, '2006-02-16', 'Nahomy Benítez Reyes', '997744');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `reservation_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `payments`
--

INSERT INTO `payments` (`id`, `amount`, `payment_method`, `reservation_id`) VALUES
(1, 250, 'CREDIT_CARD', 1),
(2, 420, 'PAYPAL', 2),
(3, 310, 'BANK_TRANSFER', 3),
(4, 190, 'CASH', 4),
(5, 650, 'CREDIT_CARD', 5),
(6, 150, 'DEBIT_CARD', 6),
(9, 190, 'Tarjeta de Crédito', 9),
(11, 420, 'Tarjeta de Crédito', 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservations`
--

CREATE TABLE `reservations` (
  `id` bigint(20) NOT NULL,
  `seat` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `flight_id` bigint(20) DEFAULT NULL,
  `passenger_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservations`
--

INSERT INTO `reservations` (`id`, `seat`, `status`, `total_price`, `flight_id`, `passenger_id`) VALUES
(1, '12A', 'CANCELLED', 250, 1, 1),
(2, '8C', 'CONFIRMED', 420, 2, 2),
(3, '15B', 'CANCELLED', 310, 3, 3),
(4, '22D', 'CANCELLED', 190, 4, 4),
(5, '3A', 'CONFIRMED', 650, 5, 5),
(6, '10F', 'CONFIRMED', 150, 6, 6),
(7, 'Económica', 'CONFIRMED', 250, 1, 9),
(8, 'Business', 'CONFIRMED', 420, 2, 10),
(9, 'Ultra Premium', 'CONFIRMED', 190, 4, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `active`, `email`, `fullname`, `password`, `role`) VALUES
(1, b'1', 'admin@gmail.com', 'System Admin', '$2a$10$sNUfsJDYiHNyosypw/DXjeF9Jd0pftrxe5ebBHvkLxKvkdl4poyrK', 'ADMIN'),
(2, b'1', 'nahomyreyes768@gmail.com', 'Nahomy Benítez Reyes', '$2a$10$RxT0QaOc1ep8c/2wsMfWvOM/UfE6fQ7q3LGBVnlH2iD8dhH5/X1Te', 'USER');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aircrafts`
--
ALTER TABLE `aircrafts`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `airlines`
--
ALTER TABLE `airlines`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `claims`
--
ALTER TABLE `claims`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4g484ost3teed97qshm20d5pj` (`reservation_id`);

--
-- Indices de la tabla `flights`
--
ALTER TABLE `flights`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo9jvf5gvgbrhgl3nh2obanw6u` (`aircraft_id`),
  ADD KEY `FKieor4j3ivp3xu584qenhfh0gd` (`airline_id`);

--
-- Indices de la tabla `passengers`
--
ALTER TABLE `passengers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKsuikipptnkvoxyitmms56j1u2` (`passport`);

--
-- Indices de la tabla `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKe7qdxh4fch1yfisduker8j6w2` (`reservation_id`);

--
-- Indices de la tabla `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKix9mwp337byu4ve2jqtjurjy6` (`flight_id`),
  ADD KEY `FKjgvjdwq4v3cp7npw20ur8h3lq` (`passenger_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aircrafts`
--
ALTER TABLE `aircrafts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `airlines`
--
ALTER TABLE `airlines`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `claims`
--
ALTER TABLE `claims`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `flights`
--
ALTER TABLE `flights`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `passengers`
--
ALTER TABLE `passengers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `claims`
--
ALTER TABLE `claims`
  ADD CONSTRAINT `FK4g484ost3teed97qshm20d5pj` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`);

--
-- Filtros para la tabla `flights`
--
ALTER TABLE `flights`
  ADD CONSTRAINT `FKieor4j3ivp3xu584qenhfh0gd` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  ADD CONSTRAINT `FKo9jvf5gvgbrhgl3nh2obanw6u` FOREIGN KEY (`aircraft_id`) REFERENCES `aircrafts` (`id`);

--
-- Filtros para la tabla `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FKp8yh4sjt3u0g6aru1oxfh3o14` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`);

--
-- Filtros para la tabla `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `FKix9mwp337byu4ve2jqtjurjy6` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`),
  ADD CONSTRAINT `FKjgvjdwq4v3cp7npw20ur8h3lq` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
