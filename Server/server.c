#include <unistd.h> 
#include <stdio.h> 
#include <sys/socket.h> 
#include <stdlib.h> 
#include <netinet/in.h> 
#include <string.h>
#include <assert.h>
#include <ctype.h>
#define PORT 8080 

//ham cut chuoi thanh 1 mang ki tu hai chieu
char** str_split(char* a_str, const char a_delim)
{
    char** result    = 0;
    size_t count     = 0;
    char* tmp        = a_str;
    char* last_comma = 0;
    char delim[2];
    delim[0] = a_delim;
    delim[1] = 0;

    while (*tmp)
    {
        if (a_delim == *tmp)
        {
            count++;
            last_comma = tmp;
        }
        tmp++;
    }

    count += last_comma < (a_str + strlen(a_str) - 1);

    count++;

    result = malloc(sizeof(char*) * count);

    if (result)
    {
        size_t idx  = 0;
        char* token = strtok(a_str, delim);

        while (token)
        {
            assert(idx < count);
            *(result + idx++) = strdup(token);
            token = strtok(0, delim);
        }
        assert(idx == count - 1);
        *(result + idx) = 0;
    }

    return result;
}

//ham update file index
void ghifile(int a[],int n)
{
    FILE *f;
    f=fopen("index.txt","wt");
    fprintf(f,"%d",n);
    for(int i=1;i<=n;i++)
        fprintf(f,"%3d",a[i]);
    fclose(f);

    // return 0;
}

int main(int argc, char const *argv[]) 
{ 
    int server_fd, new_socket, valread; 
    struct sockaddr_in address; 
    int opt = 1; 
    int addrlen = sizeof(address); 
    char mess_from_client[225];
    char buffer[1024] = "a"; 
    char *hello = "Hello from server";
    int continu = 1;
    //tao socket

    // tao file mo ta soket
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) 
    { 
        perror("socket failed"); 
        exit(EXIT_FAILURE); 
    } 
    //gan dia chi cho socket
    // gan cong port 8080 
    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, 
                                                  &opt, sizeof(opt))) 
    { 
        perror("setsockopt"); 
        exit(EXIT_FAILURE); 
    } 
    address.sin_family = AF_INET; 
    address.sin_addr.s_addr = INADDR_ANY; 
    address.sin_port = htons( PORT );  //gan cong la 8080
       
    // bind 
    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address))<0) 
    { 
        perror("bind failed"); 
        exit(EXIT_FAILURE); 
    } 

    //listen, chi dinh socket lang nghe ket noi

    if (listen(server_fd, 3) < 0) 
    { 
        perror("listen"); 
        exit(EXIT_FAILURE); 
    } 

    while(1){
        //accept, chap nhan ket noi
        
        if ((new_socket = accept(server_fd, (struct sockaddr *)&address,(socklen_t*)&addrlen))<0) 
        { 
            perror("accept"); 
            exit(EXIT_FAILURE); 
        } 

        
            char str_cli_ip[INET_ADDRSTRLEN];
            struct sockaddr_in* ip_client = (struct sockaddr_in*)&address;
            inet_ntop(AF_INET, &ip_client->sin_addr, str_cli_ip, INET_ADDRSTRLEN);
            printf("ipclient: %s\n", str_cli_ip );

            char str_cli_port[INET_ADDRSTRLEN];
            printf("port: %d\n", ntohs(ip_client->sin_port));

            
            //read, doc du lieu gan vao bien valread tra ve so byte ma no doc duoc
            valread = read(new_socket, buffer, 1024);
            printf("Danh sach file client gui len la: \n");
            //viet hoa
            // ToUp(buffer); 
            //gan bien hello tra ve cho client la buffer da viet hoa
            hello = &buffer;

            printf("%s\n",buffer );

            // send(new_socket, hello, strlen(hello), 0 );

            //cat chuoi buffer thanh 1 mang ki tu hai chieu tokens
            printf("Danh sach file cua client: \n");
            char** tokens;
            tokens = str_split(buffer, ',');
            FILE *fp;
            char kiemtratrunglap[100][1024];
            fp=fopen("index.txt","rt");
            // fscanf(f,"%d",5);
            for(int i=1;i<=100;i++){
                fscanf(fp,"%s",&kiemtratrunglap[i]);
            }
            fclose(fp);
            if (tokens)
            {
                int i;
                FILE *f;
                f=fopen("index.txt","a+");
                char port_cli[1024];
                strcpy(port_cli, *(tokens + 0));
                for (i = 1; *(tokens + i); i++)
                {
                    //hien thi len man hinh
                    printf("file=[%s]\n", *(tokens + i));

                    int check = 0;
                    //kiem tra trung lap
                    for(int l=1;l<=100;l++){
                        // fscanf(f,"%s",&kiemtratrunglap[i]);
                        char testtrugn[1024] ="";
                        strcat(testtrugn, port_cli);
                        strcat(testtrugn, ":");
                        strcat(testtrugn, str_cli_ip);
                        strcat(testtrugn, ":");
                        strcat(testtrugn, *(tokens + i));
                        if(strcmp(kiemtratrunglap[l],testtrugn) == 0){
                            check = 2;
                        }
                    }
                    if(check == 0){
                    //luu vao file
                    fprintf(f,"%s:%s:%s\n",port_cli, str_cli_ip, *(tokens + i));
                    }

                    free(*(tokens + i));
                }
                fclose(f);

                printf("\n");
                free(tokens);
            }
            //end_of_split_buffer
            
            //dem so dong
            // Open the file 
            FILE *fptest; 
            fptest = fopen("index.txt", "r");
            int count = 0;
            char c;
            for (c = getc(fptest); c != EOF; c = getc(fptest)){
                if (c == '\n'){ // Increment count if this character is newline 
                    count = count + 1;
                }
            }
            fclose(fptest); 
            printf("%d", count);

            //////////////

            //gui cho client danh sach file dang co tren server
            //read file lay ra 1 mang danh sach file tuong ung voi dia chi ip cua client
            FILE *f;
            char tokens2[1000][1024];
            f=fopen("index.txt","rt");
            // fscanf(f,"%d",5);
            for(int i=1;i<=100;i++){
                fscanf(f,"%s",&tokens2[i]);
            }
            fclose(f); 
            char output[1024] = "";
            printf("\nKet Qua Doc File:\n\n");
            for(int i=1;i<count;i++){
                // printf("%s\n",tokens2[i]);
                strcat(output, tokens2[i]);
                strcat(output, ",");
            }
            send(new_socket, output, strlen(output), 0 );
            printf("gui thanh cong danh sach file cho client: %s", output );
        
        
        close(new_socket);
    }
    return 0; 
} 

//ham viet hoa
void ToUp( char *p ) 
{ 
	while( *p ) 
	{ 
		*p=toupper( *p ); 
		p++; 
	} 
}




