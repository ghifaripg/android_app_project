-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2024 at 02:11 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `final`
--

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `post_type` varchar(10) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `item_description` text DEFAULT NULL,
  `date_lost_found` date DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `user_id`, `post_type`, `name`, `item_name`, `item_description`, `date_lost_found`, `contact_info`) VALUES
(34, 3, 'Found', '', 'Black Journaling Book', 'A small black journal found in library in the front of locker in a sofa.', '2024-04-24', '08127329237419'),
(35, 3, 'Lost', '', 'A Gold Bracelet', 'I lost a gold bracelet in a toilet building A first floor. Please contact me if you find it.', '2024-04-24', '081736482741912'),
(36, 4, 'Found', '', 'White Mouse', 'A white logitech mouse found in classroom A211 in the front seat.', '2024-04-27', '081274913842012');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `name`) VALUES
(3, 'ghifari@gmail.com', '$2y$10$K2H6Mz1o0mDajy5aasLYR.c/WbX51cjtwamsxNqJDYuXTnWfPTBWm', 'Ghifari'),
(4, 'mikail@gmail.com', '$2y$10$8bbV6J1OhoCvfQeoAekTZexw.zL9F3HAM6.ywIAitNz2MVVgd1dhe', 'mikail'),
(5, '', '$2y$10$CKdzD0Vf2tsueKjeo7laUOLFVSeYyCfsaOiUFfdAjeJ3kX66v/RSO', ''),
(6, 'gina@gmail.com', '$2y$10$Btm/i5kAwoRbg6yhitYeXO8QRj8KSu0gHZG/Y5lylkq79vIfEgA3a', 'gina');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
