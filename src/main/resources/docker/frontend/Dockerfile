FROM nginx:latest

RUN apt-get update && apt-get install -y curl \
     git  \
     npm \
     nodejs

RUN rm -rf /usr/share/nginx/*
RUN curl -sL https://deb.nodesource.com/setup_4.x | bash -

WORKDIR /usr/share/nginx
RUN git clone https://github.com/dwalldorf/owFrontend.git /usr/share/nginx
RUN npm install npm -g
RUN npm install

EXPOSE 80
CMD ["/usr/sbin/nginx", "-g", "daemon off;"]