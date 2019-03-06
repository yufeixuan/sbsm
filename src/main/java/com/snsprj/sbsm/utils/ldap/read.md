### LDAP四种模型
- 信息模型。描述 LDAP 中信息的表达方式及数据的存储结构。

#### 1. LDAP 信息模型包含三部分 Entries（条目）、Attributes（属性）、Values (值)

**Entry:** Directory中最基本的信息单元，Entry中所包含的信息描述了现实世界中的一个真实的对象，在目录系统中它可以理解为目录树中的一个节点。
在目录中添加一个 Entry 时，该 Entry 必须属于一个或多个objectClass,每一个objectClass规定了该Entry中必须要包含的属性，
以及允许使用的属性。Entry所属的类型由属性objectClass 规定。
每一个Entry都有一个DN(distinguished name)用于唯一的标志Entry在directory中的位置。

**RDN:** 在DN中最左边的内容称为相对域名。如ou=People,dc=example,dc=com其RDN为ou=People。
对于共享同一个父节点的所有节点的RDN必须是唯一的。如果不属于同一个节点则节点的RDN可以相同。

**Attribute:** 每个Entry都是由许多Attribute组成的。每一个属性(Attribute)描述的是对象的一个特征。

#### 2. 命名模型：描述数据在LDAP目录中如何进行组织与区分

#### 3. 功能模型：定义了LDAP中有关数据的操作方式，类似于关系型数据库SQL语句

#### 4. 安全模型：描述如何保证LDAP目录中的数据安全

LDAP V3 实现了SASL安全框架，SASL为多种验证协议提供了一种标准的验证方法，对于不同的验证系统，可以实现特定的SASL机制。
SASL机制代表了一种验证协议。在用户通过验证之后，可以为该用户分配附加的权限，比如一些用户只能查看特定的Entry，而不能修改。
一些用户可以查看并且修改所有的Entry等。这一过程可以理解为访问控制。

```
yum install -y openldap-servers
rpm -ql openldap-servers
systemctl start slapd
yum install -y openldap-clients
ldapsearch -x -b '' -s base '(objectclass=*)' namingContexts
systemctl status slapd
rpm -qa | grep openldap
slapd -VV
vim /etc/openldap/slapd.d/cn\=config/olcDatabase\=\{2\}hdb.ldif
```

#### install openldap centos 7

```
# install
yum install openldap openldap-servers
yum install -y openldap-clients

rpm -ql openldap

# start the openldap server daemon.
sudo systemctl start slapd
sudo systemctl enable slapd
sudo systemctl status slapd

# allow requests to the LDAP server daemon through the firewall as shown.
firewall-cmd --add-service=ldap
```

#### Configuring LDAP Server


##### create a OpenLDAP administrative user and assign a password for that user.
slappasswd
{SSHA}qKYyl3B0BN1wA/+dBScHEr7Rwgpp4g+6

##### 5. Then create an LDIF file (ldaprootpasswd.ldif) which is used to add an entry to the LDAP directory.
vim ldaprootpasswd.ldif
Add the following contents in it:

dn: olcDatabase={0}config,cn=config
changetype: modify
add: olcRootPW
olcRootPW: {SSHA}PASSWORD_CREATED

explaining the attribute-value pairs above:

- olcDatabase: indicates a specific database instance name and can be typically found inside /etc/openldap/slapd.d/cn=config.
- cn=config: indicates global config options.
- PASSWORD: is the hashed string obtained while creating the administrative user.

6. Next, add the corresponding LDAP entry by specifying the URI referring to the ldap server and the file above.
ldapadd -Y EXTERNAL -H ldapi:/// -f ldaprootpasswd.ldif


Step 3: Configuring LDAP Database
7. Now copy the sample database configuration file for slapd into the /var/lib/ldap directory, and set the correct permissions on the file.
$ sudo cp /usr/share/openldap-servers/DB_CONFIG.example /var/lib/ldap/DB_CONFIG
$ sudo chown -R ldap:ldap /var/lib/ldap/DB_CONFIG
$ sudo systemctl restart slapd

8. Next, import some basic LDAP schemas from the /etc/openldap/schema directory as follows.
$ sudo ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/cosine.ldif 
$ sudo ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/nis.ldif
$ sudo ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/inetorgperson.ldif

9. Now add your domain in the LDAP database and create a file called ldapdomain.ldif for your domain.
vim ldapdomain.ldif 
Add the following content in it (replace example with your domain and PASSWORD with the hashed value obtained before):
dn: olcDatabase={1}monitor,cn=config
changetype: modify
replace: olcAccess
olcAccess: {0}to * by dn.base="gidNumber=0+uidNumber=0,cn=peercred,cn=external,cn=auth"
  read by dn.base="cn=Manager,dc=example,dc=com" read by * none

dn: olcDatabase={2}hdb,cn=config
changetype: modify
replace: olcSuffix
olcSuffix: dc=example,dc=com

dn: olcDatabase={2}hdb,cn=config
changetype: modify
replace: olcRootDN
olcRootDN: cn=Manager,dc=example,dc=com

dn: olcDatabase={2}hdb,cn=config
changetype: modify
add: olcRootPW
olcRootPW: {SSHA}PASSWORD

dn: olcDatabase={2}hdb,cn=config
changetype: modify
add: olcAccess
olcAccess: {0}to attrs=userPassword,shadowLastChange by
  dn="cn=Manager,dc=example,dc=com" write by anonymous auth by self write by * none
olcAccess: {1}to dn.base="" by * read
olcAccess: {2}to * by dn="cn=Manager,dc=example,dc=com" write by * read

10. Then add the above configuration to the LDAP database with the following command.
$ sudo ldapmodify -Y EXTERNAL -H ldapi:/// -f ldapdomain.ldif

11. In this step, we need to add some entries to our LDAP directory. Create another file called baseldapdomain.ldif with the following content.

dn: dc=example,dc=com
objectClass: top
objectClass: dcObject
objectclass: organization
o: example com
dc: example

dn: cn=Manager,dc=example,dc=com
objectClass: organizationalRole
cn: Manager
description: Directory Manager

dn: ou=People,dc=example,dc=com
objectClass: organizationalUnit
ou: People

dn: ou=Group,dc=example,dc=com
objectClass: organizationalUnit
ou: Group 

Save the file and then add the entries to the LDAP directory.
ldapadd -x -D cn=Manager,dc=snsprj,dc=com -W -f baseldapdomain.ldif



