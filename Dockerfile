# Start from an official Jupyter base image
FROM jupyter/base-notebook:latest

# Switch to root to install system packages
USER root

# Install dependencies
RUN apt-get update && \
    apt-get install -y wget unzip curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install OpenJDK 23 (Temurin build)
RUN mkdir -p /opt/java && \
    cd /opt/java && \
    wget https://api.adoptium.net/v3/binary/latest/23/ga/linux/x64/jdk/hotspot/normal/eclipse?project=jdk -O openjdk-23.tar.gz && \
    tar -xzf openjdk-23.tar.gz --strip-components=1 && \
    rm openjdk-23.tar.gz

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/opt/java
ENV PATH=$JAVA_HOME/bin:$PATH

# Verify Java installation
RUN java --version

# Switch back to jovyan user
USER $NB_UID

# Install IJava kernel
RUN mkdir -p /tmp/ijava && \
    cd /tmp/ijava && \
    wget https://github.com/SpencerPark/IJava/releases/download/v1.3.0/ijava-1.3.0.zip && \
    unzip ijava-1.3.0.zip && \
    python3 install.py --sys-prefix && \
    cd ~ && \
    rm -rf /tmp/ijava

# Expose Jupyter Notebook
EXPOSE 8888

# Start Jupyter
CMD ["start-notebook.sh"]
