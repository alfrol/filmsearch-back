# Java Project Backend

This repository contains backend for Java project developed during the course
**Information Systems Development in Java (ITI0203)** at TalTech.

## Installation guide

To install our backend api to your server you need to follow some simple steps.

### Step 1: Clone the project

First of all you will need to clone our project so that you can access all the necessary files, configs etc.
To clone the project just copy the link and type `git clone https://github.com/alfrol/filmsearch-back.git`
at the preferred location on your machine.

### Step 2: Get a server

To run the application you will need a server. There are tons of server providers and you can choose any of them.
Just make sure that you choose Ubuntu Server.

We recommend AWS (stands for *Amazon Web Services*) because our server runs on this platform as well.
In order to configure the server on AWS you can follow these steps:

1. Go to AWS management console (can be found under *My Account* -> *AWS Management Console*).
2. In the search bar type *EC2* and click the first link.
3. Select *Launch Instance*.
4. Select Ubuntu Server 18.04 LTS (HVM), SSD Volume Type.
5. Select *t3.micro*. If you are able to select some paid tiers then you are free to do so.
6. Click *Next: Configure Instance Details* and then *Next: Add Storage*.
7. Set volume size to 16-30 GiB, leave other fields to default values.
8. Click *Next: Add Tags* and then *Next: Configure Security Group*.
9. Add new rule. Choose *All traffic* as type, source must be *0.0.0.0/0, ::/0*.
10. Click Launch and review. Then Launch.
    It will prompt you for public/private keys. Create new and save it.
    **NB! If you delete or loose the private key you won't be able to access your EC2 instance**.
11. Launch the instance.

### Step 3: Add virtual memory to the server

If you chose the AWS free tier you will probably have to add a virtual memory to the server since there is only 1 GiB.
You can follow [this](https://www.digitalocean.com/community/tutorials/how-to-add-swap-space-on-ubuntu-16-04) guide
in order to enable swap or virtual memory.

### Step 4: Connect to the server

After you have created and launched the server, you need to connect to it.
Go to your AWS Instance, click *Actions* and *Connect*.
If you are a Linux user you can follow the guide given there.
If you are a Windows user you can connect to the server using [Putty](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html?icmpid=docs_ec2_console).

### Step 5: Configure GitLab runner

Now you have to configure a GitLab runner in order to trigger pipelines on push, so that the changes actually affect
the server. For this you can follow these steps:

1. Go to [this](https://docs.gitlab.com/runner/install/linux-manually.html#install-1) site and follow the guide for Linux x86-64.
2. Now go to *GitLab* -> *Settings* -> *CI/CD* -> *Runners*.
You need the URL and token from the *Set up a specific Runner manually* field.
3. Now go to [this](https://docs.gitlab.com/runner/register/index.html) site and register runner on the server.
Use the URL and token from GitLab. Choose shell as the executor. Enter the preferred tag.
Our project uses `films-app` tag but if you want to choose something else,
you will have to change it in the `.gitlab-ci.yml` file under the *tags* property.

### Step 6: Install Docker

Since our database is running on the Docker container, you will need Docker on your server.
You can follow [this](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04) guide
in order to install Docker (until the Step 3 is enough).

### Step 7: Configure backend as a service

Now you will have to configure backend to run as a Linux service. This is convenient because it makes maintaining the 
server much easier. For this you need to follow these steps (all commands are run on the server):

1. `cd /etc/systemd/system` -> `sudo touch <name_of_the_service>.service` where <name_of_the_service> is the name you
would like to give to the service. We use `films-app` as a name. If you want to choose something else, you will 
also have to change the last line in `.gitlab-ci.yml` file - `sudo service <name_of_the_service> restart`.
2. Now copy the contents of the file
    ```text
    [Unit]
    Description=iti0203-backend service
    After=network.target
    
    [Service]
    Type=simple
    User=gitlab-runner
    WorkingDirectory=/home/gitlab-runner/api-deployment
    ExecStart=/usr/bin/java -jar iti0203-backend-1.0.0.jar --spring.config.location=../custom.yaml
    Restart=on-abort
    
    [Install]
    WantedBy=multi-user.target
    ```
3. Next do `cd /home/gitlab-runner` -> `sudo nano custom.yaml` and copy this to the file:
    ```text
    server:
        port: 8000
        servlet:
            context-path: /api
    spring:
        datasource:
            url: jdbc:mysql://localhost:3307/films
            username: alan
            password: Anloba_And_Company_31
            initialization-mode: always
            driver-class-name: com.mysql.cj.jdbc.Driver
        jpa:
            database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
            hibernate:
                ddl-auto: update
        flyway:
            url: ${spring.datasource.url}
            user: ${spring.datasource.username}
            password: ${spring.datasource.password}
            baseline-on-migrate: true
    ```
   This file contains actual configuration to the API so in case you want to change something in config just change
   this file.
4. Now do `sudo systemctl daemon-reload`
-> `sudo systemctl enable <name_of_the_service>`
-> `sudo service <name_of_the_service> restart`. Now the backend API should be up-and-running. In case you want to
check the status of the service just type `sudo service <name_of_the_service> status`.

### Step 8: Configure the frontend

Now that you have successfully configured backend you will also have to perform some configuration with frontend.
This guide can be found in the [frontend repository](https://github.com/alfrol/filmsearch-front).

## Developed By

* Aleksandr Aleksandrov (*alekal*)
* Anastassia Lobatšjova (*anloba*)
* Allan Šipovski (*alsipo*)
* Alexander Frolov (*alfrol*)
