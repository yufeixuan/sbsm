### 一、对称加密
##### 1、什么是对称加密？
对称加密就是指，加密和解密使用同一个密钥的加密方式。

##### 2、对称加密的工作过程
发送方使用密钥将明文数据加密成密文，然后发送出去，接收方收到密文后，使用同一个密钥将密文解密成明文读取。

##### 3、对称加密的优点
加密计算量小、速度块，适合对大量数据进行加密的场景。

##### 4、对称加密的两大不足
- 密钥传输问题：由于对称加密的加密和解密使用的是同一个密钥，所以对称加密的安全性就不仅仅取决于加密算法本身的强度，更取决于密钥是否被安全的保管，因此加密者如何把密钥安全的传递到解密者手里，就成了对称加密面临的关键问题。（比如，我们客户端肯定不能直接存储对称加密的密钥，因为被反编译之后，密钥就泄露了，数据安全性就得不到保障，所以实际中我们一般都是客户端向服务端请求对称加密的密钥，而且密钥还得用非对称加密加密后再传输。）

- 密钥管理问题：随着密钥数量的增多，密钥的管理问题会逐渐显现出来。比如我们在加密用户的信息时，不可能所有用户都用同一个密钥加密解密吧，这样的话，一旦密钥泄漏，就相当于泄露了所有用户的信息，因此需要为每一个用户单独的生成一个密钥并且管理，这样密钥管理的代价也会非常大。


##### 5、实际开发中使用AES加密需要注意的地方
- 服务端和我们客户端必须使用一样的密钥和初始向量IV。
- 服务端和我们客户端必须使用一样的加密模式。
- 服务端和我们客户端必须使用一样的Padding模式。

同时针对对称加密密钥传输问题这个不足：我们一般采用RSA+AES加密相结合的方式，用AES加密数据，而用RSA加密AES的密钥。同时密钥和IV可以随机生成，这要是128位16个字节就行，但是必须由服务端来生成，因为如果由我们客户端生成的话，就好比我们客户端存放了非对称加密的私钥一样，这样容易被反编译，不安全，一定要从服务端请求密钥和初始向量IV。

### 不可逆算法
MD5算法、SHA算法
SHA家族算法英文全称是Secure Hash Algorithm，中文译作安全散列算法，包括SHA-1、SHA-256等好几种算法。
SHA-1算法和MD5算法都有MD4算法导出，因此他们俩的特点、缺陷、应用场景基本是相同的。
它俩的区别在于SHA-1算法在长度上是40位十六进制，即160位的二进制，而MD5算法是32位的十六进制，即128位的二进制，所以2的160次是远远超过2的128次这个数量级的，所以SHA-1算法相对来说要比MD5算法更安全一些。

### 二、非对称加密
##### 1、什么是非对称加密？
非对称加密是指，需要用一对儿密钥，即公钥和私钥，来完成加解密的方式。如果用公钥加密，则只能用对应的私钥才能解密；而如果用私钥加密，则只能用对应的公钥才能解密。

非对称加密主要是为了解决对称加密的两大不足密钥传输问题和密钥管理问题提出的。

##### 2、对称加密的工作过程
假设A要给B发数据。
第一步：首先B生成一对儿密钥，我们称之为B私钥和B公钥，B私钥保存在B自己手里，B公钥发布给A。
第二步：A用B公钥把明文加密生成密文，发给B，B用B私钥把密文解密成明文阅读。
但这并不是说只有公钥可以用来加密，私钥也是可以用来加密的。

##### 3、非对称加密的优点

- 解决了密钥传输问题：对称加密面临的一个关键问题就是密钥的传输，因为加解密双方用的是同一个密钥，所以一旦一方的密钥泄漏了，那么整个信息传输的安全性就没有保证了。而非对称加密，使用的是两个、一对儿密钥，只要双方事先发布好，密钥就不必参与传输，因此非对称加密相对对称加密更加安全。

- 解决了密钥管理问题：上面提到对称加密要给每一对用户单独维护一个密钥，这样随着密钥数量的增多，管理起来麻烦。而非对称加密是一对儿密钥，自己保存私钥，公钥可以发布给很多人，这样就达到了一对多的效果，不必维护很多密钥。

##### 4、非对称加密的不足
加密计算量大、速度慢，适合对少量数据进行加密的场景。

##### 5、常用非对称加密算法
RSA加密算法

### 加解密流程
1. 服务器端(server)和客户端(client)分别生成自己的密钥对
2. server和client分别交换自己的公钥
3. client生成AES密钥(aesKey)
4. client使用自己的RSA私钥(privateKey)对请求明文数据(params)进行数字签名
5. 将签名加入到请求参数中，然后转换为json格式
6. client使用aesKey对json数据进行加密得到密文(data)
7. client使用sever的RSA公钥对aesKey进行加密(encryptkey)
8. 分别将data和encryptkey作为参数传输给服务器端
##### 服务器端进行请求响应时将上面流程反过来即可






