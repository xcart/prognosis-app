FROM nginx

# install dehydrated and essential tools
RUN apt-get update -qq && apt-get -y install apache2-utils curl && apt-get -y install dehydrated

EXPOSE 443

# copy over the script that is run by the container
COPY ./webserver.sh /tmp
RUN ["chmod", "+x", "/tmp/webserver.sh"]

ENV DOMAIN solutions.x-cart.com
ENV CA_SSL true
# where we store everything SSL-related
ENV SSL_ROOT /etc/ssl/

# copy our Nginx config template
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY ./dehydrated.conf /etc/dehydrated/config

# Define the script we want run once the container boots
# Use the "exec" form of CMD so Nginx shuts down gracefully on SIGTERM (i.e. `docker stop`)
CMD [ "/tmp/webserver.sh" ]