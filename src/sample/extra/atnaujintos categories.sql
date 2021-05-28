-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 27, 2021 at 10:49 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `task`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `lft` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rght` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `lft`, `name`, `rght`) VALUES
(1, 1, 'Home', 286),
(2, 2, 'Skydai', 27),
(3, 3, 'Lauko skydai', 4),
(4, 5, 'Vidaus skydai', 26),
(5, 6, 'Metalinės dėžės', 7),
(6, 8, 'Potinkiniai skydeliai', 13),
(7, 9, 'Metaliniai', 10),
(8, 11, 'Plastikiniai', 12),
(9, 14, 'Virštinkiniai skydeliai', 23),
(10, 15, 'Metaliniai', 16),
(11, 17, 'Plastikiniai', 22),
(12, 18, 'IP40', 19),
(13, 20, 'IP65', 21),
(14, 24, 'Remontiniai skydeliai', 25),
(15, 28, 'Apšvietimas', 77),
(16, 29, 'Lauko apšvietimas', 46),
(17, 30, 'Gatviniai šviestuvai', 31),
(18, 32, 'Lauko prožektoriai', 33),
(19, 34, 'Parkiniai šviestuvai', 35),
(20, 36, 'Atramos gembės', 43),
(21, 37, 'Atramos', 38),
(22, 39, 'Gembės', 40),
(23, 41, 'Pamatai', 42),
(24, 44, 'Priedai lauko apšvietimui', 45),
(25, 47, 'Vidaus apšvietimas', 76),
(26, 48, 'LED panelės 60x60', 49),
(27, 50, 'LED panelės', 55),
(28, 51, 'IP20', 52),
(29, 53, 'IP44', 54),
(30, 56, 'Downlight', 61),
(31, 57, 'IP20', 58),
(32, 59, 'IP44', 60),
(33, 62, 'Lubiniai IP65', 63),
(34, 64, 'Sieniniai', 69),
(35, 65, 'IP44', 66),
(36, 67, 'IP65', 68),
(37, 70, 'Pakabinami', 71),
(38, 72, 'Avarinis apšvietimas', 73),
(39, 74, 'High Bay', 75),
(40, 78, 'Kabeliai', 127),
(41, 79, 'Instaliaciniai kabeliai', 80),
(42, 81, 'Jėgos kabeliai', 88),
(43, 82, 'NYY-J', 83),
(44, 84, 'CYKY-J', 85),
(45, 89, 'Behalogeniniai kabeliai', 100),
(46, 92, 'Cca', 93),
(47, 94, 'B2ca', 95),
(48, 101, 'Kontroliniai kabeliai', 102),
(49, 103, 'Aliuminiai kabeliai', 104),
(50, 105, 'Laidai', 106),
(51, 107, 'Internetinai kabeliai', 122),
(52, 108, 'Cat5', 113),
(53, 109, 'UTP', 110),
(54, 111, 'FTP', 112),
(55, 114, 'Cat6', 119),
(56, 115, 'UTP', 116),
(57, 117, 'FTP', 118),
(58, 128, 'Vamzdžiai ir gofra', 167),
(59, 129, 'Lauko', 144),
(60, 130, 'APE', 131),
(61, 132, 'Gofros', 139),
(62, 133, '450N', 134),
(63, 135, '750N', 136),
(64, 137, '1250N', 138),
(65, 140, 'Prakalimo vamzdis', 141),
(66, 142, 'Sudedamas vazdis', 143),
(67, 145, 'Vidaus', 166),
(68, 146, 'Gofros', 151),
(69, 147, '320N', 148),
(70, 149, '750N', 150),
(71, 152, 'Behalogeninės gofros', 157),
(72, 158, 'Vamzdžiai', 159),
(73, 160, 'Behalogeniniai vamzdžiai', 161),
(74, 162, 'Gofros su kabeliu', 163),
(75, 164, 'Gofros su laidu', 165),
(76, 168, 'Instaliacinės prekės', 219),
(77, 169, 'Jungikliai ir kištukiniai lizdai', 180),
(78, 170, 'Potinkiniai jungikliai ir kištukiniai lizdai', 171),
(79, 172, 'Virštinkiniai jungikliai ir kištukiniai lizdai', 177),
(80, 178, 'Pramoniniai lizdai ir kištukai', 179),
(81, 181, 'Potinkinės dėžutės', 186),
(82, 182, 'Dėžutė į mūrą', 183),
(83, 184, 'Dėžutė į gipsą', 185),
(84, 187, 'Šildymo elementai', 194),
(85, 188, 'Šildymo kilimėliai', 189),
(86, 190, 'Šildymo kabeliai', 191),
(87, 192, 'Šildymo įranga', 193),
(88, 195, 'Judesio ir būvio jutikliai', 208),
(89, 196, 'Judesio', 201),
(90, 197, 'virštinkiniai', 198),
(91, 199, 'Potinkiniai', 200),
(92, 202, 'Būvio', 207),
(93, 203, 'virštinkiniai', 204),
(94, 205, 'Potinkiniai', 206),
(95, 209, 'Virštinkinės instaliacinės dėžutės', 210),
(96, 211, 'Grindinės dėžutės', 216),
(97, 212, 'Plastikinės', 213),
(98, 214, 'Metalinės', 215),
(99, 217, 'Modulinė 45x45 sistema', 218),
(100, 220, 'Metalinės konstrukcijos', 235),
(101, 221, 'Kopėčios', 226),
(102, 222, 'Karšto cinkavimo', 223),
(103, 224, 'Šalto cinkavimo', 225),
(104, 227, 'Loveliai', 232),
(105, 228, 'Karšto cinkavimo', 229),
(106, 230, 'Šalto cinkavimo', 231),
(107, 233, 'Apšvietimo lovelis', 234),
(108, 236, 'Įžeminimas ir žaibosauga', 259),
(109, 237, 'Įžeminimo strypai', 242),
(110, 238, 'Variuoti strypai', 239),
(111, 240, 'Cinkuoti strypai', 241),
(112, 243, 'Cinkuota juosta', 244),
(113, 245, 'Cinkuota viela', 246),
(114, 247, 'Jungtys', 248),
(115, 249, 'Laikikliai', 250),
(116, 251, 'Aktyvi žaibosauga', 252),
(117, 253, 'Pasyvi žaibosauga', 254),
(118, 255, 'Viršįtampio ribotuvai', 256),
(119, 257, 'Priedai', 258),
(120, 260, 'Elektromechanika', 285),
(121, 261, 'Automatiniai jungikliai', 266),
(122, 262, '6kA', 263),
(123, 264, '10kA', 265),
(124, 271, 'Valdymo relės', 272),
(125, 273, 'Kontaktoriai', 274),
(126, 275, 'Kirtikliai', 280),
(127, 276, 'Moduliniai', 277),
(128, 278, 'Paneliniai', 279),
(129, 281, 'Moduliniai jungikliai', 282),
(130, 283, 'Saugikliai', 284),
(131, 86, 'R2v su XLPE', 87),
(132, 90, 'Dca', 91),
(133, 96, 'Ekranuotas B2ca', 97),
(134, 98, 'Nedegus kabelis', 99),
(135, 120, 'Cat6a', 121),
(136, 123, 'Antgaliai kabeliui', 124),
(137, 125, 'Movos kabeliui', 126),
(138, 153, '320N', 154),
(139, 155, '750N', 156),
(140, 173, 'IP20', 174),
(141, 175, 'IP44', 176),
(142, 267, 'Srovės nuotekio relės', 268),
(143, 269, 'Srovės nuotekio relės kartu su automatiniu jungikliu', 270);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
