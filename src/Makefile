#
# Filename:    Makefile
# Author:      Izzet Yilmaz
# Dated:       May 15, 2021
# Description: Makefile for the simple compiler
#

SHELL := /bin/sh
MAKE  := make
ROOT  := ../src
COMPILER_PKG  := ./compiler
HELPER_PKG	  := ./helper
UTIL_PKG	  := ./utilities

# Directories
# INCLUDES := ./compiler
# BIN_DIR  := ./bin

CC      := javac
RM      := rm
JAR     := jar
JFLAGS  := cmfv
CFLAGS  := -Xlint
LDFLAGS :=
LIBS    :=
.SUFFIXES: .java .class
MANIFEST_FILE := manifest.txt

echo_cmd = @echo   "  $(1)$(ECHO_FILE_NAME)" ; $(2)

MAKE := $(MAKE) --no-print-directory --silent
CC   := $(call echo_cmd,CC      ,$(CC))
LD   := $(call echo_cmd,LD      ,$(LD))
RM   := $(call echo_cmd,RM      ,$(RM))

usage:
	@echo ""
	@echo "Makefile for the compiler."
	@echo ""
	@echo "Usage:"
	@echo "	make all        # Compile everything"
	@echo "	make clean      # Remove all object files"
	@echo "	make clean_all  # Remove all object files and the binary"
	@echo ""

MAIN_DIR		= $(COMPILER_PKG)
LEXICAL_DIR		= $(COMPILER_PKG)/lexical
SEMANTIC_DIR 	= $(COMPILER_PKG)/semantic
HELPER_DIR		= $(HELPER_PKG)
UTIL_DIR		= $(UTIL_PKG)
STATEMENT_DIR   = $(COMPILER_PKG)/statements

SRCS =	$(HELPER_DIR)/Helper.java \
		$(UTIL_DIR)/Console.java \
		$(STATEMENT_DIR)/Statement.java \
		$(STATEMENT_DIR)/ReadStatement.java \
		$(STATEMENT_DIR)/WriteStatement.java \
		$(STATEMENT_DIR)/AssignStatement.java \
		$(STATEMENT_DIR)/IfStatement.java \
		$(STATEMENT_DIR)/RepeatStatement.java \
		$(LEXICAL_DIR)/Lexical.java \
		$(SEMANTIC_DIR)/Semantic.java \
		$(SEMANTIC_DIR)/SymbolTable.java \
		$(SEMANTIC_DIR)/Row.java \
		$(MAIN_DIR)/Main.java
		

all: $(SRCS) compiler.jar

OBJS = $(SRCS:.java=.class)

compiler.jar:  $(OBJS)
	$(JAR) $(JFLAGS) $(MANIFEST_FILE) compiler.jar $(OBJS)

.java.class:
	$(CC) $(CFLAGS) $<

clean:
	$(RM) $(OBJS)

clean_all:
	$(RM) $(OBJS) compiler.jar
