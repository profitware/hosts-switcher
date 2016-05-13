package ru.profitware.hosts_switcher;

class HostsSwitcher {
    public static void main(String[] args) {
        def (os_name, sep) = ['os.name', 'line.separator'].collect { 
            System.getProperty(it).toLowerCase(); 
        };

        def hosts_file = new File(
            os_name.indexOf('win') >= 0 
            ? 'C:/Windows/System32/Drivers/etc/hosts' 
            : '/etc/hosts'
        );

        def hosts_content = hosts_file.getText();

        def (hostname, ip) = args;

        if (hosts_content.contains(hostname)) {
            hosts_file.write(
                hosts_content.readLines().collect { 
                    it.contains(hostname) ? "$ip $hostname" : it 
                }.join(sep)
            );

            hosts_file << sep;
        } else {
            hosts_file.write("$hosts_content$sep$ip $hostname$sep");
        }

    }
}
