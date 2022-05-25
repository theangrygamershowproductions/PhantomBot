# Enable Twitch embeds

Due to changes by Twitch, the chat and live feed panel can no longer be displayed without a proper SSL setup.

To make it work:
- SSL certificate required
- URL must not contain a port number
- URL must not be an IP Address

NOTE: If you access your panel at _localhost:25000_, the port number requirement does not apply.

If you are not interested in the Twitch chat and live feed panels, you do not need to make any changes to your bot.

There are three methods for fixing the embeds

Method 1 is the easiest and compatible with most setups, but does not give access to EventSub

Method 2 or 3 is **required** to use EventSub, but Method 3 is preferred

Method 3 is recommended if the bot is hosted on a server

## Method 1: PhantomBot Remote Panel

We have setup a remote copy of the panel on this GitHub Pages site that is fully comliant with Twitch's changes.

This panel does not send any data to us. All communication of credentials and commands is directly with your bot.

### Setup SSL

The bot should generate a self-signed SSL certificate automatically for you.

The bot will automatically generate and renew this certificate for you as long as:
- You either do not have a `usehttps=` line in your _botlogin.txt_, or it is set to `true`
- You either do not have a `httpsFileName=` line in your _botlogin.txt_, or it has no value after the `=`

#### Trust the Self-Signed Certificate

Since the SSL certificate is self-signed, your browser/OS will not trust it by default.

##### OBS/XSplit

As of PhantomBot v3.3.5, all browser-source endpoints, such as the death counter and alerts, now support being accessed without SSL, to bypass self-signed SSL certificate issues with OBS and XSplit.

Simply change the `https://` part at the beginning of the browser source URL to `http://`

##### Windows and Linux

Connect to your bot's built-in webserver (eg. `https://192.168.0.2:25000/`) and your browser will warn you about the certificate being untrusted.

Your browser should provide a _More_ or a _Details_ button which will then reveal some kind of _Proceed Anyway_ or _Accept Risk & Continue_ button.

Once you have done this and the bot's main page has loaded, you're done with this step.

##### macOS

**NOTE:** Steps 2 and 3 are based on using Chrome for Mac.

1. Connect to your bot's built-in webserver (eg. `https://192.168.0.2:25000/`) and your browser will warn you about the certificate being untrusted.

2. In the address bar, click the little lock with the X. This will bring up a small information screen. Click the button that says _Certificate Information_.

3. Click and drag the image that looks like a little certificate to your desktop.

4. Double-click the certificate on your desktop. This will bring up the _Keychain Access_ utility. Enter your password to unlock it.

5. Be sure you add the certificate to the **System** keychain, not the login keychain. Click _Always Trust_, even though this doesn’t seem to do anything.

6. After the certificate has been added, double-click it in the _Keychain Access_ certificate list. You may have to authenticate again.

7. Expand the _Trust_ section. Find the option _When using this certificate_ and set it to _Always Trust_.

8. Close and restart the browser.

### Launch the PhantomBot Remote Panel

Once SSL is setup and trusted, just navigate to [PhantomBot](https://phantombot.github.io/PhantomBot/) and launch the panel from there.

&nbsp;

## Method 2: Setup SSL and Change the Baseport

For this method, follow the steps in Method 1 for setting up and trusting the self-signed SSL certificate, or install your own SSL certificate from a trusted internet Certificate Authority.

After setting up the SSL certificate, change (or add, if this line is missing) the `baseport=` in your _botlogin.txt_ to a value of 443.

Once you restart the bot you can access the panel without using a port number (eg. `https://bot.my.domain`).

**NOTE:** This method is not compatible with bots running on servers or other devices that already have a webserver running on them with SSL enabled.

**NOTE:** You may have to run the bot as root or setup a service user that has specifically been granted access to port 443.

**NOTE:** This method does not work, for the purposes of enabling embeds, when accessing the bot by IP address. A domain, sub-domain, or hostname is required.

&nbsp;

## Method 3: Setup a Reverse Proxy

If you are running the bot on a server that already has a running webserver with SSL enabled, you can lookup how to setup a reverse proxy on a subdomain that links to the bot's webserver.

If you have the webserver and a domain, but have not yet setup SSL, consider looking into [Let's Encrypt](https://letsencrypt.org/) and using the [Certbot](https://certbot.eff.org/) client to get a free basic SSL certificate for life.

#### Certbot Setup

1. Pre-setup the configuration for your webserver from below. Ignore the instruction to edit the path to the certificate files

2. Download and run Certbot using the instructions at https://certbot.eff.org/instructions to acquire a certificate, which will also update the certificate paths on your server and setup a cron job to renew it

3. Test the SSL setup. You should be able to access `https://mybot.mydomain.com/` (replace with appropriate domain) and have the padlock icon or other successful SSL identifier in your browsers address bar

#### NGINX Sample Config

**NOTE:** When using this method, port 25000 should be ***blocked*** in your firewall and the following lines should be in your _botlogin.txt_
```
# botlogin.txt
usehttps=false
proxybypasshttps=true
```

```
# NGINX Config
upstream phantombot {
    server 127.0.0.1:25000; # set this to the IP:Baseport of the bot
}

server {
    listen 80;
    listen [::]:80;
    server_name <servername>; # Server name is the domain or sub-domain
    return 301 https://$server_name;
}

server {
    listen              443 ssl http2;
    server_name         <servername>; # Server name is the domain or sub-domain
    large_client_header_buffers 4 32k; # Very important to prevent unexplained 400 errors
    ssl_certificate /etc/letsencrypt/live/<folder>/fullchain.pem; # managed by Certbot, replace with the appropriate path
    ssl_certificate_key /etc/letsencrypt/live/<folder>/privkey.pem; # managed by Certbot, replace with the appropriate path
    ssl_protocols       TLSv1.3;
    ssl_ciphers ECDH+AESGCM:DH+AESGCM:ECDH+AES256:DH+AES256:ECDH+AES128:DH+AES:ECDH+3DES:DH+3DES:RSA+AESGCM:RSA+AES:RSA+3DES:!aNULL:!MD5;
 
    location / {
        proxy_pass http://phantombot;
        proxy_set_header Host $host;
    }
 
    error_log /var/log/nginx/twitch_error.log;
    access_log /var/log/nginx/twitch_access.log;
}
```

* Replace the 2 instances of `<servername>` with the domain or sub-domain the bot will live on
* _(Skip this if you will be installing/running certbot after creating the config)_ Replace the 2 instances of `<folder>` with the specific folder that houses the letsencrypt certificates. The folder name is usually whichever domain certbot chooses as the primary
* If the bot is running on a port other than 25000, change it in the upstream section


#### Apache httpd Sample Config

**NOTE:** Proxying with Apache requires Apache httpd 2.2.15 or later with all of the following Apache modules: mod_alias, mod_proxy, mod_proxy_http, mod_proxy_wstunnel

**NOTE:** When using this method, the baseport (default 25000) should be ***blocked*** in your firewall (iptables/firewall-cmd) and the following lines should be in your _botlogin.txt_
```
# botlogin.txt
usehttps=false
proxybypasshttps=true
```

If your Apache configuration is setup with a _conf.d_ folder, such as _/etc/httpd/conf.d_, then it is best to put this config into a new file ending in _.conf_ in that folder, for example _/etc/httpd/conf.d/phantombot.conf_

```
<VirtualHost *:80>
    # Sets the document root (base folder for html files) and the domain served
    DocumentRoot /var/www/html   # A valid folder that Apache can access, needed for certbot http-01 challenge
    ServerName <servername>      # Domain or sub-domain name

    # Enables logging
    # Error log
    ErrorLog ${APACHE_LOG_DIR}/error.log
    # Access (inbound request) log
    CustomLog ${APACHE_LOG_DIR}/access.log combined

    # Forces all HTTP connections to redirect to HTTPS (SSL), except for certbot http-01 challenges, which require HTTP
    RedirectMatch 301 ^/(?!.wellknown) "https://<servername>$1"  # Do NOT add a slash after the <servername>, it should look like https://mybot.mydomain.com$1
</VirtualHost>

<VirtualHost *:443>
    # Sets the domain served
    ServerName <servername>  # Domain or sub-domain name

    # Enables and configures SSL
    SSLEngine On
    # Includes a config file from letsencrypt that sets some SSL options, such as allowed SSL versions
    Include                 /etc/letsencrypt/options-ssl-apache.conf    # If not using letsencrypt/certbot, delete this line
    SSLCertificateFile      /etc/letsencrypt/live/<folder>/cert.pem     # Path to cert.pem (Signed Certificate)
    SSLCertificateKeyFile   /etc/letsencrypt/live/<folder>/privkey.pem  # Path to privkey.pem (Private Key)
    SSLCertificateChainFile /etc/letsencrypt/live/<folder>/chain.pem    # Path to chain.pem (Certificate Signature Chain)

    # Enables and configures proxying
    ProxyRequests On
    # Proxies inbound requests going to the bot
    ProxyPass "/ws/" "ws://127.0.0.1:25000/ws/"                         # Points to the bot running on localhost:25000, change the port number if necessary
    # Rewrites outbound redirect headers from the bot to show the domain from Apache instead
    ProxyPassReverse "/ws/" "ws://127.0.0.1:25000/ws/"                  # Points to the bot running on localhost:25000, change the port number if necessary
    ProxyPass "/" "http://127.0.0.1:25000/"                             # Points to the bot running on localhost:25000, change the port number if necessary
    ProxyPassReverse "/" "http://127.0.0.1:25000/"                      # Points to the bot running on localhost:25000, change the port number if necessary
    ProxyPreserveHost On                                                # Maintain proper hostname for redirects

    # Enables logging
    ErrorLog ${APACHE_LOG_DIR}/error.log
    CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>
```

* Replace the 3 instances of `<servername>` with the domain or sub-domain the bot will live on
* _(Skip this if you will be installing/running certbot after creating the config)_ Replace the 3 instances of `<folder>` with the specific folder that houses the letsencrypt certificates. The folder name is usually whichever domain certbot chooses as the primary
* If the document root for the Apache instance is not _/var/www/html_, change it accordingly
* If the bot is running on a port other than 25000, change it in the ProxyPass directives

#### Docker

* If your webserver is hosted directly on your server, with Docker only handling the bot, see the setup instructions above

* If your webserver is hosted on a Docker container

    * Option 1: Setup the webserver container as above, but change references to _127.0.0.1:25000_ to point to the PhantomBot container by name (eg. _phantombot:25000_)

    * Option 2: Use an intermediary Docker container that auto-configures proxying and letsencrypt, such as nginxproxy/nginx-proxy with nginxproxy/acme-companion
