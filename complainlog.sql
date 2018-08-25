-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2018 at 02:54 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `complainlog`
--

-- --------------------------------------------------------

--
-- Table structure for table `complain`
--

CREATE TABLE `complain` (
  `reportedBy` varchar(100) DEFAULT NULL,
  `rptbyDepartment` varchar(100) DEFAULT NULL,
  `problem` varchar(100) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `personnel` varchar(100) DEFAULT NULL,
  `pDepartment` varchar(100) DEFAULT NULL,
  `Txtmessage` varchar(500) DEFAULT NULL,
  `dateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `complain`
--

INSERT INTO `complain` (`reportedBy`, `rptbyDepartment`, `problem`, `comment`, `status`, `personnel`, `pDepartment`, `Txtmessage`, `dateTime`) VALUES
('Bernard Aggrey', 'Accountancy', 'Internet Connection', 'cannot connect to internet', 'now', 'Cosmos Hagan', 'IT Directories', 'Hello Cosmos Hagan, Please your service is need at Accountancy. Reported By Bernard Aggrey', '2018-07-08 23:45:18');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `id` varchar(20) NOT NULL,
  `deprt_Name` varchar(200) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`id`, `deprt_Name`, `location`) VALUES
('DP001', 'Accountancy', 'Administration Block'),
('DP002', 'Transport ', 'Transport Office'),
('DP004', 'Electrical Engineering', 'Electrical Block'),
('DP005 ', 'IT Directories', 'MP Block'),
('EST01', 'PUMBING', 'BTech Extension');

-- --------------------------------------------------------

--
-- Table structure for table `hod`
--

CREATE TABLE `hod` (
  `id` varchar(20) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `department` varchar(200) DEFAULT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hod`
--

INSERT INTO `hod` (`id`, `name`, `department`, `designation`, `username`, `password`, `phone`) VALUES
('Admin001', 'ADMIN', 'System Admin', 'Head Of Department', 'admin', '12345', '273509432'),
('KTS0001', 'Cosmos Hagan', 'IT Directories', 'Head Of Department', 'cosmoshagan', 'cosmatic', '273509432'),
('KTS0003', 'Johnson Kusi', 'Transport ', 'Head Of Department', 'johnson', 'skinnie', '273509432');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hod`
--
ALTER TABLE `hod`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
