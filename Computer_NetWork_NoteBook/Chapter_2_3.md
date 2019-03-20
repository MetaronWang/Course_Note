# Chapter_2_3


## P2P Application

### Pure P2P architecture

*    No always on server
*    Arbutrary end systenms directly communicate
*    Peers are intermittently connected and change IP address
*    Example:
        *    File distribution(BitTorrent)
        *    Streaming
        *    VoIP(Skip)


### File distribution time:P2P:
*   Server transmission: must upload at least one copy
    *   time to send one copy $\frac{F}{u_s}$
*   Client Each client must download file copy
    *   min client download time: $\frac{F}{d_{min}}$
*   Clients: As aggregate must download NF bits
    *   max upload  rate(limiting max download rate) is $u_s+\sum_{u_i}$

### P2P file distribution: BitTorrent:
*   File divided into 256Kb chunks
*   peers in torrent send/receive file chunks
****
*   Tracker: tracks peers participating in torrent 
    *   Tells client list of peers.
*   Torrent: Group of peers exchanging chunks of a file
*   peer join torrent:
    *   has no chunks, but will accumulate them over time from other peers
    *   registers with tracker to get list of peers, connects to subset of peers 

#### Bittorrent: Request, Sending:
*   Request Chunk
    *   在任意的时间, 每一个peer都应当有file chunks的不同子集(即每个chunks只会在服务器中被下载一次)
    *   一个peer会向其他peer请求他们所拥有的chunks列表
    *   会优先请求最稀有的那个chunks



#### BitTorrent: tit-for-tat
*   每一个客户端从别的客户端处下载
*   每一个客户端充当别的客户端的下载服务器
*   Higher upload rate: Find better trading partners, get file faster!


##  Video Streaming and content distribution network(CDNS)

*   *Challenge*
    *   scale: - how to reach ~1 Billion users.
    *   Heterogeneity:
        *   Different users have different capabilities



## Socket Programming
*   Two Socket types for transport services:
    *   UDP
    *   TCP

### Clien/Server scoket interaction:UDP
*   Server(Running on Server IP):L
    *   Create socket,port = x
    *   serverSocket = socket(AF_INET,SOCK_DGRAM)
*   Client
    *   create socket:
    *   clientSocket =socket(AF_INET,SOCK_DGRAM)


