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

        switch (args.size()) {
            case 0: hosts_content; break;
            case 1: hosts_content = hosts_content.readLines().findAll {
                        !it.contains(args[0])
                    }.join(sep); break;
            default: hosts_content = args.inject(false) {
                        acc, arg ->
                        acc |= hosts_content.contains(arg);
                        return acc;
                     }
                   ? hosts_content.readLines().collect {
                         host_line ->
                         return args.inject(false) {
                             acc, arg ->
                             acc |= host_line.contains(arg);
                             return acc;
                         } ? args.join(' ') : host_line;
                     }.join(sep)
                   : "$hosts_content$sep${ args.join(' ') }$sep";
        }

        hosts_file.write(hosts_content)
    }
}
