Install  traceroute

```bash
sudo apt-get install traceroute
```

See what ping does

```bash
man ping
```

To send pings, can use this format

- by default a packet is 56 bytes

```bash
ping www.csail.mit.edu
```

- to send 10 packets, in interval of 5s, size 512 byes

```bash
ping -c 10 -i 5 -s 512 www.csail.mit.edu
```