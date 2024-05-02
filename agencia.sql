-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-05-2024 a las 02:30:40
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_code` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `seats_business` int(11) DEFAULT NULL,
  `seats_economy` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `deleted`, `departure_date`, `destination`, `flight_code`, `origin`, `seats_business`, `seats_economy`) VALUES
(1, b'0', '2024-05-03', 'Barcelona', 'MABA-8874', 'Madrid', 20, 18),
(2, b'0', '2024-05-05', 'Madrid', 'BAMA-1145', 'Barcelona', 20, 20),
(3, b'0', '2024-05-03', 'Valencia', 'MAVA-1548', 'Madrid', 20, 20),
(4, b'0', '2024-05-05', 'Madrid', 'VAMA-6543', 'Valencia', 20, 20),
(5, b'0', '2024-05-03', 'Bilbao', 'MABI-2289', 'Madrid', 20, 20),
(6, b'1', '2024-05-05', 'Madrid', 'BIMA-7791', 'Bilbao', 30, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking`
--

CREATE TABLE `flight_booking` (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `people_quantity` int(11) DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `seats_business_taken` int(11) DEFAULT NULL,
  `seats_economy_taken` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `flight_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `deleted`, `people_quantity`, `departure_date`, `destination`, `origin`, `seats_business_taken`, `seats_economy_taken`, `user_id`, `flight_id`) VALUES
(1, b'0', 2, '2024-05-03', 'Barcelona', 'Madrid', 0, 2, 1, 1),
(2, b'1', 4, '2024-05-03', 'Barcelona', 'Madrid', 3, 3, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking_seq`
--

CREATE TABLE `flight_booking_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking_seq`
--

INSERT INTO `flight_booking_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_seq`
--

CREATE TABLE `flight_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight_seq`
--

INSERT INTO `flight_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `city`, `deleted`, `hotel_code`, `name`) VALUES
(1, 'Madrid', b'0', 'MA-12345', 'Madrid Resort'),
(2, 'Barcelona', b'0', 'BAR-12547', 'Hotel Vela'),
(3, 'Valencia', b'1', 'VAL-1002', 'Hotel Costa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_seq`
--

CREATE TABLE `hotel_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `hotel_seq`
--

INSERT INTO `hotel_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `room_capacity` int(11) DEFAULT NULL,
  `room_code` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `deleted`, `room_capacity`, `room_code`, `room_type`, `hotel_id`) VALUES
(1, b'0', 2, 'MAR-101', 'Double', 1),
(2, b'0', 2, 'MAR-102', 'Double', 1),
(3, b'0', 3, 'MAR-103', 'Triple', 1),
(52, b'1', 3, 'MAR-202', 'Triple', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking`
--

CREATE TABLE `room_booking` (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `people_quantity` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking`
--

INSERT INTO `room_booking` (`id`, `deleted`, `people_quantity`, `city`, `departure_date`, `entry_date`, `hotel_id`, `room_type`, `user_id`, `room_id`) VALUES
(1, b'0', 2, 'Madrid', '2024-05-05', '2024-05-02', 1, 'Double', 1, 1),
(2, b'0', 2, 'Madrid', '2024-05-08', '2024-05-06', 1, 'Double', 1, 1),
(3, b'0', 2, 'Madrid', '2024-05-08', '2024-05-06', 1, 'Double', 2, 2),
(4, b'0', 2, 'Madrid', '2024-05-06', '2024-05-05', 1, 'Double', 2, 2),
(52, b'0', 2, 'Madrid', '2024-05-13', '2024-05-10', 1, 'Double', 52, 2),
(53, b'0', 2, 'Madrid', '2024-05-13', '2024-05-10', 1, 'Double', 2, 1),
(54, b'0', 2, 'Madrid', '2024-05-14', '2024-05-13', 1, 'Double', 1, 1),
(102, b'0', 2, 'Madrid', '2024-05-10', '2024-05-08', 1, 'Double', 52, 2),
(152, b'0', 2, 'Madrid', '2024-05-15', '2024-05-14', 1, 'Double', 52, 1),
(153, b'0', 2, 'Madrid', '2024-05-19', '2024-05-18', 1, 'Double', 52, 1),
(202, b'0', 3, 'Madrid', '2024-05-05', '2024-05-01', 1, 'Triple', 1, 3),
(252, b'0', 2, 'Madrid', '2024-05-22', '2024-05-20', 1, 'Double', 1, 1),
(352, b'1', 3, 'Madrid', '2024-05-08', '2024-05-05', 1, 'Triple', 52, 3),
(353, b'0', 3, 'Madrid', '2024-05-09', '2024-05-05', 1, 'Triple', 52, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking_seq`
--

CREATE TABLE `room_booking_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking_seq`
--

INSERT INTO `room_booking_seq` (`next_val`) VALUES
(451);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_seq`
--

CREATE TABLE `room_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room_seq`
--

INSERT INTO `room_seq` (`next_val`) VALUES
(151);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `deleted`, `dni`, `email`, `name`, `surname`) VALUES
(1, b'0', '70148745P', 'pm@gmail.com', 'Pablo', 'Martín'),
(2, b'0', '78412565C', 'av@gmail.com', 'Ana', 'Velázquez'),
(52, b'0', '74125863Q', 'mb@gmail.com', 'Manuel', 'Barbero'),
(102, b'1', '74201589S', 'sr@gmail.com', 'Samuel', 'Rodrígez');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_seq`
--

CREATE TABLE `user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `user_seq`
--

INSERT INTO `user_seq` (`next_val`) VALUES
(201);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKenows5co1dgrri8scbjm37i82` (`user_id`),
  ADD KEY `FK3uiklmjy1d7ba6rrjp6iq08kq` (`flight_id`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`);

--
-- Indices de la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4ucaqqglq7yk5auh0jdwkctku` (`user_id`),
  ADD KEY `FKiwt0ws97ta91ukd4xonewjbxl` (`room_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK3uiklmjy1d7ba6rrjp6iq08kq` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`),
  ADD CONSTRAINT `FKenows5co1dgrri8scbjm37i82` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Filtros para la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD CONSTRAINT `FK4ucaqqglq7yk5auh0jdwkctku` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKiwt0ws97ta91ukd4xonewjbxl` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
