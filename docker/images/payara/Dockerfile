FROM payara/server-full:5.193

ADD drivers/ ${PAYARA_DIR}/glassfish/lib/
ADD scripts/post-boot-commands.asadmin /opt/payara/scripts/init_0_datasources.sh
