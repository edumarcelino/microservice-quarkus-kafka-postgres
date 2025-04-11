package br.com.loader;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.com.domain.repository.CategoriaRepository;
import br.com.domain.repository.TagRepository;
import br.com.domain.repository.UsuarioRepository;
import br.com.domain.model.Categoria;
import br.com.domain.model.Tag;
import br.com.domain.model.Usuario;

@ApplicationScoped
public class DatabaseLoader {

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        System.out.println("⚙️ Iniciando carga de dados no banco...");

        if (categoriaRepository.count() == 0) {
            String[] categorias = {
                    "Ação", "Aventura", "RPG", "Simulação", "Esporte", "Corrida", "Terror",
                    "Puzzle / Quebra-Cabeças", "Indie", "Multiplayer Online", "Casual",
                    "Educacional", "Realidade Virtual", "E-sports", "Retrô"
            };
            for (String nome : categorias) {
                categoriaRepository.persist(new Categoria(nome));
            }
            System.out.println("✅ Categorias de games criadas.");
        }

        if (tagRepository.count() == 0) {
            String[] tags = {
                    // Gêneros
                    "FPS", "Battle Royale", "Roguelike", "Metroidvania", "Hack and Slash", "Soulslike",
                    "Sandbox", "Stealth", "Turn-Based",
                    // Plataformas
                    "PC", "Xbox", "PlayStation", "Nintendo Switch", "Mobile", "VR", "Web",
                    // Mecânicas
                    "Coop", "PvP", "PvE", "Crafting", "Loot", "Construção", "Exploração",
                    // Tecnologias
                    "Unreal Engine", "Unity", "Godot", "Ray Tracing", "Blockchain", "Crossplay", "IA nos Games", // encurtado
                    // Temas
                    "Fantasia", "Sci-fi", "Pós-apocalíptico", "Cyberpunk", "Medieval", "Mitologia", "Espaço"
            };

            for (String nome : tags) {
                tagRepository.persist(new Tag(nome));
            }
            System.out.println("✅ Tags de games criadas.");
        }

        if (usuarioRepository.findByEmail("admin@admin.com") == null) {
            Usuario admin = new Usuario();
            admin.setNome("admin") ;
            admin.setEmail("admin@admin.com");
            admin.setRole(br.com.domain.model.enums.Role.ADMIN); 
            admin.setSenhaHash(BcryptUtil.bcryptHash("admin123"));  // senha padrão
            admin.setAvatarUrl("https://i.pravatar.cc/150?img=1"); 
            admin.setPreferenciaNotificacao(true);

            usuarioRepository.persist(admin);
            
            System.out.println("✅ Usuário administrador criado.");
        } else {
            System.out.println("ℹ️ Usuário administrador já existe.");
        }
    }
}
