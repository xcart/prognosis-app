#!/bin/bash

# creates self-signed SSL files
# these files are used in development and get production up and running so dehydrated can do its work
create_pems() {
  openssl req \
      -x509 \
      -nodes \
      -newkey rsa:2048 \
      -keyout privkey.pem \
      -out "$SSL_CERT_HOME"/fullchain.pem \
      -days 3650 \
      -sha256 \
      -config <(cat <<EOF
[ req ]
prompt = no
distinguished_name = subject
x509_extensions    = x509_ext

[ subject ]
commonName = $DOMAIN

[ x509_ext ]
subjectAltName = @alternate_names

[ alternate_names ]
DNS.1 = localhost
IP.1 = 127.0.0.1
EOF
)

  openssl dhparam -out "$SSL_CERT_HOME"/dhparam.pem 2048
  chmod 600 *.pem
}

# if we have not already done so initialize Docker volume to hold SSL files
if [ ! -d "$SSL_CERT_HOME" ]; then
  mkdir -p "$SSL_CERT_HOME"
  chmod 755 "$SSL_ROOT"
  # shellcheck disable=SC2086
  chmod -R 700 $SSL_ROOT/certs
  cd "$SSL_CERT_HOME" || exit
  create_pems
  cd "$SSL_ROOT" || exit
  echo "$DOMAIN" > /etc/dehydrated/domains.txt
fi

# if we are configured to run SSL with a real certificate authority run dehydrated to retrieve/renew SSL certs
if [ "$CA_SSL" = "true" ]; then

  mkdir -p /var/www/dehydrated

  # Nginx must be running for challenges to proceed
  # run in daemon mode so our script can continue
  nginx

#  # accept the terms of letsencrypt/dehydrated
  dehydrated --register --accept-terms
#
#  # retrieve/renew SSL certs
  dehydrated --cron

  (crontab -l ; echo "0 0 * * 1 /usr/bin/dehydrated --cron >/dev/null 2>&1") | crontab -
#
#  # copy the fresh certs to where Nginx expects to find them
  # cp "$SSL_ROOT"/certs/"$DOMAIN"/fullchain.pem "$SSL_ROOT"/certs/"$DOMAIN"/privkey.pem "$SSL_CERT_HOME"
#
#  # pull Nginx out of daemon mode
  nginx -s stop
fi

# start Nginx in foreground so Docker container doesn't exit
nginx -g "daemon off;"