-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tripdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tripdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tripdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema tripdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tripdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tripdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tripdb` ;

-- -----------------------------------------------------
-- Table `tripdb`.`members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`members` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`members` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `pass` TEXT NOT NULL,
  `email` VARCHAR(100) NOT NULL unique,
  `profile_uri` VARCHAR(255) NULL DEFAULT 'noimg.jpg',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripdb`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`board` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`board` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(255) NOT NULL,
  `title` VARCHAR(500) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`no`),
  INDEX `board_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `tripdb`.`members` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripdb`.`security`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`security` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`security` (
  `user_id` VARCHAR(255) NOT NULL,
  `salt` TEXT NOT NULL,
  `iteration` INT NOT NULL,
  `algo` VARCHAR(45) NULL DEFAULT 'SHA-256',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `security_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `tripdb`.`members` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripdb`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`comments` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_no` INT NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  `comment` TEXT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `comments_board_no_fk_idx` (`board_no` ASC) VISIBLE,
  INDEX `comments_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `comments_board_no_fk`
    FOREIGN KEY (`board_no`)
    REFERENCES `tripdb`.`board` (`no`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `comments_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `tripdb`.`members` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `tripdb` ;

-- -----------------------------------------------------
-- Table `tripdb`.`sido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`sido` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tripdb`.`gugun`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`gugun` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripdb`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tripdb`.`attraction_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`attraction_info` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `tripdb`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `tripdb`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripdb`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tripdb`.`attraction_description`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`attraction_description` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripdb`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tripdb`.`attraction_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`attraction_detail` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripdb`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tripdb`.`swear`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`swear` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`swear` (
  `word` VARCHAR(500) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripdb`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`notice` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`notice` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `user_Id` VARCHAR(255) NOT NULL,
  `title` VARCHAR(500) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`no`),
  INDEX `talk_user_id_fk_idx` (`user_Id` ASC) VISIBLE,
  CONSTRAINT `talk_user_id_fk`
    FOREIGN KEY (`user_Id`)
    REFERENCES `tripdb`.`members` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tripdb`.`reset_tokens`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `tripdb`.`reset_tokens`;

CREATE TABLE `tripdb`.`reset_tokens` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` VARCHAR(255) NOT NULL,
    `token` VARCHAR(100),
    `expiry` TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES members(id)
);

-- -----------------------------------------------------
-- Table `tripdb`.`file_info`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `tripdb`.`file_info` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`file_info` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `board_no` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `original_file` VARCHAR(50) NULL,
  `save_file` TEXT,
  PRIMARY KEY (`idx`),
  CONSTRAINT `file_info_to_board_article_no_fk`
    FOREIGN KEY (`board_no`)
    REFERENCES `tripdb`.`board` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tripdb`.`search_terms`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `tripdb`.`search_terms` ;

CREATE TABLE search_terms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content_id INT NOT NULL,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- Table `tripdb`.`plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`plan`;

CREATE TABLE IF NOT EXISTS `tripdb`.`plan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(255) NOT NULL,
  `plan_name` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `hit` INT NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `share_status` TINYINT(1) NOT NULL DEFAULT 0,  -- 공유 여부 컬럼 추가
  PRIMARY KEY (`id`),
  CONSTRAINT `plan_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `tripdb`.`members` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tripdb`.`route_plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`route_plan` ;

CREATE TABLE IF NOT EXISTS `tripdb`.`route_plan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  `sequence` INT,
  PRIMARY KEY (`id`),
CONSTRAINT `route_plan_id_fk`
    FOREIGN KEY (`plan_id`)
    REFERENCES `tripdb`.`plan` (`id`)
    ON DELETE cascade
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripdb`.`likes_plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripdb`.`likes_plan` ;

CREATE TABLE likes_plan (
    user_id VARCHAR(255),
    plan_id INT,
    PRIMARY KEY (user_id, plan_id),
    FOREIGN KEY (user_id) REFERENCES members(id),
    FOREIGN KEY (plan_id) REFERENCES plan(id)
);


ALTER TABLE security DROP FOREIGN KEY security_user_id_fk;
ALTER TABLE security ADD CONSTRAINT new_security_user_id_fk FOREIGN KEY (user_id) REFERENCES members(id) ON DELETE CASCADE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO members (id, name, pass, email) 
VALUES 
('ssafy', '김싸피', 'NvXPmT+PT7w49pz0pHCoVX+9Y5uXCCHuT6ErZUvTcvM=', 'ssafy@ssafy.com'),
('admin', '관리자', 'eMBf0tlTQyR/tGTyYw2Tk2PcjLF4bbcP53E+EcGQngE=', 'admin@naver.com');

INSERT INTO security (user_id, salt, iteration, algo) 
VALUES 
('admin', 'N2C80dZQmjZpZ7troK8wVQ==', 120, 'SHA-256'),
('ssafy', 'v67h4ouQTDiUggkoSj7zAQ==', 55, 'SHA-256');

INSERT INTO notice (title, content, user_id) 
VALUES 
('안녕하세요', '이번에 여행 사이트를 새로 오픈하게 되었습니다. 잘 부탁드립니다.', 'admin'),
('JJTrip 사용방법', '관광지 검색 메뉴를 통해 카카오 맵을 기반으로 하여 지역별 관광지, 숙박, 축제, 식당 등의 여러 정보를 검색하실 수 있으며 지도에 마커를 연결하여 여행 경로를 직접 설정하여 쉽게 여행 계획을 세울 수 있습니다. 많은 이용 부탁드립니다.', 'admin'),
('일상의 아름다움을 발견하며', '오늘은 어제와는 조금 다른 하루가 되길 바랍니다. 작은 변화에도 주목하고, 일상의 아름다움을 발견하며 삶을 즐겨보세요. 바쁜 일상에 몰두하다가도 가끔은 주변을 둘러보고, 사소한 행복을 느끼는 것이 중요합니다. 작은 것 하나하나가 모여 큰 행복을 이루는 것을 잊지 마세요. 오늘 하루도 행복하시길 바랍니다.', 'admin'),
('일상의 아름다움을 발견하1며', '오늘은 어제와는 조금 다른 하루가 되길 바랍니다. 작은 변화에도 주목하고, 일상의 아름다움을 발견하며 삶을 즐겨보세요. 바쁜 일상에 몰두하다가도 가끔은 주변을 둘러보고, 사소한 행복을 느끼는 것이 중요합니다. 작은 것 하나하나가 모여 큰 행복을 이루는 것을 잊지 마세요. 오늘 하루도 행복하시길 바랍니다.', 'admin');

insert into board(title, content, user_id)
values('여행 일정 안내', '다음 주 토요일에 여행을 떠나기로 했습니다. 참여를 희망하시는 분들은 댓글로 참여 의사를 밝혀주세요.','admin'),
  ('관광지 추천해주세요!!', '최근 방문한 관광지 중에 추천할만한 곳이 있으면 알려주세요. 함께 가고 싶은 분들이 있어요!', 'admin'),
  ('부산 관광지 추천합니다', '부산 해운대 경치가 너무 좋아요! 꼭 한번 가보세요!', 'ssafy'),
  ('다음주 제주도 갈 예정입니다. 제주도 관광지 추천해주세요', '최근 제주도에 방문하신 분들 중 관광지 중에 추천할만한 곳이 있으면 알려주세요.', 'ssafy'),
  ('강릉 해수욕장 추천', '강릉 망상 해수욕장 적극 추천합니다!', 'admin'),
  ('안녕하세요 질문드립니다.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. nec semper dui rutrum id.', 'admin'),
  ('전주 비빔밥 맛집 어딘가요', 'Phasellus eget venenatis elit, non hendrerit ipsum. Phasellus quis lacus viverra, ultricies neque vel, vestibulum odio. Maecenas vel venenatis lectus, ut viverra lectus. Quisque elementum dolor vitae maximus blandit. Duis libero massa, pulvinar nec enim sit amet, varius imperdiet enim. Sed ornare felis mauris, et pharetra augue lobortis eu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Integer ornare bibendum tempor. Ut venenatis diam quis ipsum imperdiet vehicula fermentum eu tortor. Etiam at dignissim felis.', 'admin'), 
  ('부산 국밥은 어디가 맛있나요??', ' Maecenas nec ornare dui, lobortis volutpat risus. Fusce tincidunt orci est, ut lobortis lorem varius sit amet. Aliquam ac vestibulum justo. Donec feugiat commodo massa, semper faucibus odio euismod maximus. Duis tincidunt justo et mauris condimentum elementum a a libero. Nulla sit amet dolor cursus, lacinia arcu nec, mattis metus. Vestibulum vestibulum fringilla eros ut euismod. Fusce varius cursus sollicitudin. Duis convallis lectus sit amet tortor ultricies ornare. Aliquam malesuada lorem lectus, ac vulputate velit eleifend in. Proin dui augue, rutrum a gravida in, sollicitudin ac ipsum.', 'admin'),
  ('안녕하세요 질문!!!', 'lobortis volutpat risus. Fusce tincidunt orci est, ut lobortis lorem varius sit amet. Aliquam ac vestibulum justo. Donec feugiat commodo massa, semper faucibus odio euismod maximus. Duis tincidunt justo et mauris condimentum elementum a a libero. Nulla sit amet dolor cursus, lacinia arcu nec, mattis metus. Vestibulum vestibulum fringilla eros ut euismod. Fusce varius cursus sollicitudin. Duis convallis lectus sit amet tortor ultricies ornare. Aliquam malesuada lorem lectus, ac vulputate velit eleifend in. Proin dui augue, rutrum a gravida in, sollicitudin ac ipsum.', 'admin');